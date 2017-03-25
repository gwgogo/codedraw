#include <stdio.h>
#include <windows.h>
#include "FunctionExport.h"

extern "C" __declspec(dllexport) int WordAnalyze(wchar_t *str, wchar_t *buffer, UINT nSize) {
	//�ʼ�
    static const wchar_t wcHead[] = {L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��',
           L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��'};

    //�߼�
    static const wchar_t wcMid[] = {L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��',
          L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��'};

    //����
    static const wchar_t wcTail[] = {L' ', L'��', L'��', L'��', L'��', L'��', L'��',
		L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��',
		L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��', L'��'};

    UINT pos = 0;

    while(*str != '\0')  {
		if(*str < 256) {
			if(pos + 2 >= nSize - 1)
                break;
            buffer[pos] = *str;
            ++pos;
        } else { 
			if(pos + 4 >= nSize - 1)
                break;
            buffer[pos] = wcHead[(*str - 0xAC00) / (21 * 28)];
            buffer[pos + 1] = wcMid[(*str - 0xAC00) % (21 * 28) / 28];
            buffer[pos + 2] = wcTail[(*str - 0xAC00) % 28];

            pos += 3;
        }
        ++str;
    }
    buffer[pos] = '\0';

    return pos;
}