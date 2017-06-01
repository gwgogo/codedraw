#include <stdio.h>
#include <stdlib.h> 
#include <windows.h>
#include <locale.h>
#include "FunctionPointer.h"
#include "opencv2/highgui/highgui.hpp"

#define SIZE 4096

unsigned int mGetModule(HINSTANCE *const hInst, LPCWSTR path);
unsigned int mReleaseModule(HINSTANCE *hInst);
const wchar_t *const mGetHangle(HINSTANCE hInst);
unsigned int mInitBrailleQueue(HINSTANCE bdInst, const wchar_t *const ParsingHangle);
void drawBraille(HINSTANCE bdInst, unsigned int count, const wchar_t *const ParsingHangle);

int main(int argc, char **agrv) {
	
	HINSTANCE waInst;
	HINSTANCE bdInst;

	mGetModule(&waInst, L"WordAnalyzer.dll");
	mGetModule(&bdInst, L"BrailleDrawer.dll");	
	
	while(1) {
		const wchar_t *const ParsingHangle = mGetHangle(waInst);
		unsigned int count = mInitBrailleQueue(bdInst, ParsingHangle);
		drawBraille(bdInst, count, ParsingHangle);
	}
	
	
	mReleaseModule(&waInst);
	mReleaseModule(&bdInst);

    return 0;
}

unsigned int mGetModule(HINSTANCE *const hInst, LPCWSTR path) {
	*hInst = LoadLibrary(path);

    if(hInst == NULL)
        return 1;
	return 0;
}

unsigned int mReleaseModule(HINSTANCE *const hInst) {
	FreeLibrary(*hInst);
	return 0;
}

const wchar_t *const mGetHangle(HINSTANCE waInst) {
	setlocale(LC_ALL, "Korean");

	wchar_t StreamTemp[SIZE];
	wchar_t *BufferTemp = (wchar_t *)malloc(sizeof(wchar_t) * SIZE);
	
	printf("건물명 입력 :  ");
	wscanf(L"%s", StreamTemp);

	int num = _wtoi(StreamTemp);
	if(num != 0)
		BufferTemp = StreamTemp;
	else{
		dWordAnalyze FuncWordAnalyze = (dWordAnalyze)GetProcAddress(waInst, "WordAnalyze");
		FuncWordAnalyze(StreamTemp, BufferTemp, (sizeof BufferTemp) * SIZE);
	}

	return BufferTemp;
}

unsigned int mInitBrailleQueue(HINSTANCE bdInst, const wchar_t *const ParsingHangle) {
	int count = 0;
	for(unsigned int index = 0; index < wcslen(ParsingHangle); index++) {
		if(ParsingHangle[index] != L' ') {
			if(ParsingHangle[index] == L'ㄲ' || ParsingHangle[index] == L'ㄸ' || ParsingHangle[index] == L'ㅃ' || ParsingHangle[index] == L'ㅆ' || ParsingHangle[index] == L'ㅉ') {
				count += 2;
			} else {
				count += 1;
			}
		} else {
			count += 1;
		}
	}
	dInitBrailleQueue FuncInitBrailleQueue = (dInitBrailleQueue)GetProcAddress(bdInst, "InitBrailleQueue");
	FuncInitBrailleQueue(count, ParsingHangle);

	return count;
}

void drawBraille(HINSTANCE bdInst, unsigned int count, const wchar_t *const ParsingHangle){

	dDrawBraille FuncDrawBraille = (dDrawBraille)GetProcAddress(bdInst, "DrawBraille");
	FuncDrawBraille(count, ParsingHangle);
}
