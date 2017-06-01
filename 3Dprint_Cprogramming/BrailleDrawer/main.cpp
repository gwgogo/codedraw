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
			if(ParsingHangle[index] == L'��' || ParsingHangle[index] == L'��' || ParsingHangle[index] == L'��' || ParsingHangle[index] == L'��' || ParsingHangle[index] == L'��'){
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
	char *brailleName[10] = {"����1.png", "����2.png", "����3.png", "����4.png", "����5.png", "����6.png", "����7.png", "����8.png", "����9.png","����10.png"};


	IplImage *dstImage;		// IplImage�� �̹��� �����
	dstImage = cvCreateImage(cvSize(600, 400), IPL_DEPTH_8U, 1); 
	cvSet(dstImage, cvScalarAll(255));

	Node *current = bQueue.front;

	//BrailleCheckFunc(dstImage, ParsingHangle, &bQueue);
	if(_wtoi(ParsingHangle) != 0){	// �Է��� ���� ���ڸ�
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
	else{		// �Է��� ���� �ѱ��̸�
		for(unsigned int index = 0; index < wcslen(ParsingHangle); index++){
			if(index % 3 == 0){			// �ʼ��̸�
				if(current->item.ModelFlag == 1){	// �ʼ��� �ȼҸ��̸�
					DrawHeadAccent(dstImage, current->item.ModelTag);
					current =current->next;
					if(ParsingHangle[index] == L'��')
						DrawHeadGiyeok(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadDigeut(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadBieup(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadSiot(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadJieut(dstImage, current->item.ModelTag);
				}
				else {								//�ʼ��� �ȼҸ��� �ƴϸ�
					if(ParsingHangle[index] == L'��')
						DrawHeadGiyeok(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadNieun(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadDigeut(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadRieul(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadMieum(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadBieup(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadSiot(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadIeung(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadJieut(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadChieut(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadKieuk(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadTieut(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadPieup(dstImage, current->item.ModelTag);
					else if(ParsingHangle[index] == L'��')
						DrawHeadHieut(dstImage, current->item.ModelTag);
				}
			}
			else if(index % 3 == 1){	// �߼�
				if(ParsingHangle[index] == L'��')
					DrawMidA(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidYa(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidEo(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidYeo(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidO(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidYo(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidU(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidYu(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidEu(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidI(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidAe(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidE(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidOe(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidOa(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidUeo(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidEui(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawMidYeoi(dstImage, current->item.ModelTag);
			}

			else if(index % 3 == 2){	// ����
				if(ParsingHangle[index] == L' ')
					DrawTailBlank(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailGiyeok(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailNieun(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailDigeut(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailRieul(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailMieum(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailBieup(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailSiot(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailIeung(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailJieut(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailChieut(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailKieuk(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailTieut(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailPieup(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailHieut(dstImage, current->item.ModelTag);
				else if(ParsingHangle[index] == L'��')
					DrawTailDoubleSiot(dstImage, current->item.ModelTag);
			}

			current = current->next;
		}
	}


	cvNamedWindow("Drawing Graphics", CV_WINDOW_AUTOSIZE);	// ���������
	cvShowImage("Drawing Graphics", dstImage);	// �׸�
	cvWaitKey(0);	// ����� ���, 0�̸� Ű�����Է������� ���, �и������������ ��������

	cvSaveImage(brailleName[i++], dstImage);	// ����
	cvDestroyAllWindows();
	cvReleaseImage(&dstImage);
}