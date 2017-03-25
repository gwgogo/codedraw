/*#include <stdio.h>
#include "BrailleModel.h"
#include "BrailleQueue.h"


void BrailleCheckFunc(IplImage *dstImage, const wchar_t *const ParsingHangle, Queue *bQueue){
	
	Node *current = bQueue->front;

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
			else {	//초성이 된소리가 아니면
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
*/