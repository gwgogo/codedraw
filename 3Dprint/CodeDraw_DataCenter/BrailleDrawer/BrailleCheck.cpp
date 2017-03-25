/*#include <stdio.h>
#include "BrailleModel.h"
#include "BrailleQueue.h"


void BrailleCheckFunc(IplImage *dstImage, const wchar_t *const ParsingHangle, Queue *bQueue){
	
	Node *current = bQueue->front;

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
			else {	//�ʼ��� �ȼҸ��� �ƴϸ�
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
*/