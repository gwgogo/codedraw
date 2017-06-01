#include <stdio.h>
#include <stdlib.h>
#include "BrailleQueue.h"
#include "FunctionExport.h"


Queue bQueue;
int i = 0;

extern "C" __declspec(dllexport) void InitBrailleQueue(unsigned int count, const wchar_t *const ParsingHangle) {
	InitializeQueue(&bQueue);

	for(unsigned index = 0, tag=0; index < wcslen(ParsingHangle); index++, tag++) {
		ModelBraille *temp = (ModelBraille *)malloc(sizeof ModelBraille);
		ModelBraille *temp2 = NULL;
		if(ParsingHangle[index] != L' '){
			if(ParsingHangle[index] == L'ㄲ' || ParsingHangle[index] == L'ㄸ' || ParsingHangle[index] == L'ㅃ' || ParsingHangle[index] == L'ㅆ' || ParsingHangle[index] == L'ㅉ'){
				temp->ModelFlag = 1;
				temp2 = (ModelBraille *)malloc(sizeof ModelBraille);
			}
			else
				temp->ModelFlag = 0;
		}
		else
			temp->ModelFlag = 0;
		temp->ModelTag = tag;
		addQueue(*temp, &bQueue);
		free(temp);
		if(temp2 != NULL){
			temp2->ModelFlag = 0;
			temp2->ModelTag = ++tag;
			addQueue(*temp2, &bQueue);
			free(temp2);
		}

	}

	/*Node *current = bQueue.front;
	while(current != NULL) {
		printf("%lu", current->item.ModelTag);
		current = current->next;
	}*/


}

extern "C" __declspec(dllexport) void DrawBraille(unsigned int count, const wchar_t *const ParsingHangle){
	char *brailleName[10] = {"점자1.png", "점자2.png", "점자3.png", "점자4.png", "점자5.png", "점자6.png", "점자7.png", "점자8.png", "점자9.png","점자10.png"};


	IplImage *dstImage;		// IplImage는 이미지 저장소
	dstImage = cvCreateImage(cvSize(600, 400), IPL_DEPTH_8U, 1); 
	cvSet(dstImage, cvScalarAll(255));

	Node *current = bQueue.front;

	//BrailleCheckFunc(dstImage, ParsingHangle, &bQueue);
	if(_wtoi(ParsingHangle) != 0){	// 입력한 것이 숫자면
		for(unsigned int index = 0; index < wcslen(ParsingHangle); index++){
			if(ParsingHangle[index] == L'1')
				DrawNumOne(dstImage, current->item.ModelTag);
			else if(ParsingHangle[index] == L'2')
				DrawNumTwo(dstImage, current->item.ModelTag);
			else if(ParsingHangle[index] == L'3')
				DrawNumThree(dstImage, current->item.ModelTag);
			else if(ParsingHangle[index] == L'4')
				DrawNumFour(dstImage, current->item.ModelTag);
			else if(ParsingHangle[index] == L'5')
				DrawNumFive(dstImage, current->item.ModelTag);
			else if(ParsingHangle[index] == L'6')
				DrawNumSix(dstImage, current->item.ModelTag);
			else if(ParsingHangle[index] == L'7')
				DrawNumSeven(dstImage, current->item.ModelTag);
			else if(ParsingHangle[index] == L'8')
				DrawNumEight(dstImage, current->item.ModelTag);
			else if(ParsingHangle[index] == L'9')
				DrawNumNine(dstImage, current->item.ModelTag);
			else if(ParsingHangle[index] == L'0')
				DrawNumZero(dstImage, current->item.ModelTag);
			current = current->next;
		}
	}
	else{		// 입력한 것이 한글이면
		for(unsigned int index = 0; index < wcslen(ParsingHangle); index++){
			if(index % 3 == 0){			// 초성이면
				if(current->item.ModelFlag == 1){	// 초성이 된소리이면
					DrawHeadAccent(dstImage, current->item.ModelTag);
					current =current->next;
					if(ParsingHangle[index] == L'ㄲ')
						DrawHeadGiyeok(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㄸ')
						DrawHeadDigeut(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㅃ')
						DrawHeadBieup(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㅆ')
						DrawHeadSiot(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㅉ')
						DrawHeadJieut(dstImage, current->item.ModelTag);
				}
				else {								//초성이 된소리가 아니면
					if(ParsingHangle[index] == L'ㄱ')
						DrawHeadGiyeok(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㄴ')
						DrawHeadNieun(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㄷ')
						DrawHeadDigeut(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㄹ')
						DrawHeadRieul(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㅁ')
						DrawHeadMieum(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㅂ')
						DrawHeadBieup(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㅅ')
						DrawHeadSiot(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㅇ')
						DrawHeadIeung(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㅈ')
						DrawHeadJieut(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㅊ')
						DrawHeadChieut(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㅋ')
						DrawHeadKieuk(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㅌ')
						DrawHeadTieut(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㅍ')
						DrawHeadPieup(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'ㅎ')
						DrawHeadHieut(dstImage, current->item.ModelTag);
				}
			}
			else if(index % 3 == 1){	// 중성
				if(ParsingHangle[index] == L'ㅏ')
					DrawMidA(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅑ')
					DrawMidYa(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅓ')
					DrawMidEo(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅕ')
					DrawMidYeo(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅗ')
					DrawMidO(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅛ')
					DrawMidYo(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅜ')
					DrawMidU(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅠ')
					DrawMidYu(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅡ')
					DrawMidEu(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅣ')
					DrawMidI(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅐ')
					DrawMidAe(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅔ')
					DrawMidE(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅚ')
					DrawMidOe(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅘ')
					DrawMidOa(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅝ')
					DrawMidUeo(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅢ')
					DrawMidEui(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅖ')
					DrawMidYeoi(dstImage, current->item.ModelTag);
			}

			else if(index % 3 == 2){	// 종성
				if(ParsingHangle[index] == L' ')
					DrawTailBlank(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㄱ')
					DrawTailGiyeok(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㄴ')
					DrawTailNieun(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㄷ')
					DrawTailDigeut(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㄹ')
					DrawTailRieul(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅁ')
					DrawTailMieum(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅂ')
					DrawTailBieup(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅅ')
					DrawTailSiot(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅇ')
					DrawTailIeung(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅈ')
					DrawTailJieut(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅊ')
					DrawTailChieut(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅋ')
					DrawTailKieuk(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅌ')
					DrawTailTieut(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅍ')
					DrawTailPieup(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅎ')
					DrawTailHieut(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'ㅆ')
					DrawTailDoubleSiot(dstImage, current->item.ModelTag);
			}

			current = current->next;
		}
	}


	cvNamedWindow("Drawing Graphics", CV_WINDOW_AUTOSIZE);	// 윈도우셋팅
	cvShowImage("Drawing Graphics", dstImage);	// 그림
	cvWaitKey(0);	// 출력후 대기, 0이면 키보드입력전까지 대기, 밀리세컨드단위로 설정가능

	cvSaveImage(brailleName[i++], dstImage);	// 저장
	cvDestroyAllWindows();
	cvReleaseImage(&dstImage);
}