#include <stdio.h>
#include <stdlib.h>
#include "BrailleQueue.h"
#include "FunctionExport.h"
#include "opencv2/highgui/highgui.hpp"

/*#define RADIUS 4		// 1.5mm
#define GAP	12			// 2.5mm 하나의 점자내에서 점당 거리
#define SPACE 17		// 4.5mm 점자부호 사이의 거리

#define HEAD_SP_X 10			// 초성 X좌표의 Starting point
#define HEAD_SP_Y 10			// 초성 Y좌표의 Starting point
*/

#define RADIUS 4		// 1.5mm
#define GAP	12			// 2.5mm 하나의 점자내에서 점당 거리
#define SPACE 17		// 4.5mm 점자부호 사이의 거리

#define HEAD_SP_X 10			// 초성 X좌표의 Starting point
#define HEAD_SP_Y 10			// 초성 Y좌표의 Starting point

void DrawTest(IplImage *dstImage,int index, int arr[]){
	for(int i = 0; i < 6; i++){
		if(arr[i] == -1){		
			if(i==0)
				cvCircle(dstImage, cvPoint(HEAD_SP_X +		 (GAP + SPACE) * index, HEAD_SP_Y + GAP * 0),	RADIUS, cvScalarAll(0), arr[i], 0 );	// 첫번째 줄 좌
			else if(i==1)
				cvCircle(dstImage, cvPoint(HEAD_SP_X +		 (GAP + SPACE) * index, HEAD_SP_Y + GAP * 1),	RADIUS, cvScalarAll(0), arr[i], 0 );	// 두번째 줄 좌
			else if(i==2)
				cvCircle(dstImage, cvPoint(HEAD_SP_X +		 (GAP + SPACE) * index, HEAD_SP_Y + GAP * 2),	RADIUS, cvScalarAll(0), arr[i], 0 );	// 세번째 줄 좌
			else if(i==3)
				cvCircle(dstImage, cvPoint(HEAD_SP_X + GAP + (GAP + SPACE) * index, HEAD_SP_Y + GAP * 0),	RADIUS, cvScalarAll(0), arr[i], 0 );	// 첫번째 줄 우
			else if(i==4)
				cvCircle(dstImage, cvPoint(HEAD_SP_X + GAP + (GAP + SPACE) * index, HEAD_SP_Y + GAP * 1),	RADIUS, cvScalarAll(0), arr[i], 0 );	// 두번째 줄 우
			else if(i==5)
				cvCircle(dstImage, cvPoint(HEAD_SP_X + GAP + (GAP + SPACE) * index, HEAD_SP_Y + GAP * 2),	RADIUS, cvScalarAll(0), arr[i], 0 );	// 세번째 줄 우
			
		}
	}
}


/////===================초성========================
void DrawHeadGiyeok(IplImage *dstImage,int index){
	int arr[6] = {1, 1, 1, -1, 1, 1};
	DrawTest(dstImage, index, arr);
}

void DrawHeadNieun(IplImage *dstImage,int index){
	int arr[6] = {-1, 1, 1, -1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawHeadDigeut(IplImage *dstImage,int index){
	int arr[6] = {1, -1, 1, -1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawHeadRieul(IplImage *dstImage,int index){
	int arr[6] = {1, 1, 1, 1, -1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawHeadMieum(IplImage *dstImage,int index){
	int arr[6] = {-1, 1, 1, 1, -1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawHeadBieup(IplImage *dstImage,int index){
	int arr[6] = {1, 1, 1, -1, -1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawHeadSiot(IplImage *dstImage,int index){
	int arr[6] = {1, 1, 1, 1, 1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawHeadIeung(IplImage *dstImage,int index){
	int arr[6] = {1, 1, 1, 1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawHeadJieut(IplImage *dstImage,int index){
	int arr[6] = {1, 1, 1, -1, 1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawHeadChieut(IplImage *dstImage,int index){
	int arr[6] = {1, 1, 1, 1, -1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawHeadKieuk(IplImage *dstImage,int index){
	int arr[6] = {-1, -1, 1, -1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawHeadTieut(IplImage *dstImage,int index){
	int arr[6] = {-1, -1, 1 ,1 -1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawHeadPieup(IplImage *dstImage,int index){
	int arr[6] = {-1, 1, 1, -1, -1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawHeadHieut(IplImage *dstImage,int index){
	int arr[6] = {1, -1, 1, -1, -1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawHeadAccent(IplImage *dstImage,int index){
	int arr[6] = {1, 1, 1, 1, 1, -1};
	DrawTest(dstImage, index, arr);
}



/////===================중성========================
void DrawMidA(IplImage *dstImage,int index){
	int arr[6] = {-1, -1, 1, 1, 1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawMidYa(IplImage *dstImage,int index){
	int arr[6] = {1, 1, -1, -1, -1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawMidEo(IplImage *dstImage,int index){
	int arr[6] = {1, -1, -1, -1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawMidYeo(IplImage *dstImage,int index){
	int arr[6] = {-1, 1, 1, 1, -1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawMidO(IplImage *dstImage,int index){
	int arr[6] = {-1, 1, -1, 1 ,1 -1};
	DrawTest(dstImage, index, arr);
}
void DrawMidYo(IplImage *dstImage,int index){
	int arr[6] = {1, 1, -1, -1, 1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawMidU(IplImage *dstImage,int index){
	int arr[6] = {-1, 1, -1, -1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawMidYu(IplImage *dstImage,int index){
	int arr[6] = {-1, 1, 1, -1, 1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawMidEu(IplImage *dstImage,int index){
	int arr[6] = {1, -1, 1, -1, 1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawMidI(IplImage *dstImage,int index){
	int arr[6] = {-1, 1 ,-1, 1, -1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawMidAe(IplImage *dstImage,int index){
	int arr[6] = {-1, -1, -1, 1, -1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawMidE(IplImage *dstImage,int index){
	int arr[6] = {-1, 1, -1, -1, -1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawMidOe(IplImage *dstImage,int index){	// 'ㅚ'
	int arr[6] = {-1, 1, -1, -1, -1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawMidOa(IplImage *dstImage,int index){
	int arr[6] = {-1, -1, -1, 1, 1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawMidUeo(IplImage *dstImage,int index){	// 'ㅝ'
	int arr[6] = {-1, -1, -1, -1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawMidEui(IplImage *dstImage,int index){
	int arr[6] = {1, -1, 1, -1, -1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawMidYeoi(IplImage *dstImage,int index){
	int arr[6] = {1, 1, -1, -1, 1, 1};
	DrawTest(dstImage, index, arr);
}




/////===================중성========================
void DrawTailBlank(IplImage *dstImage, int index){
	int arr[6] = {1, 1, 1, 1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawTailGiyeok(IplImage *dstImage, int index){
	int arr[6] = {-1, 1, 1, 1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawTailNieun(IplImage *dstImage,int index){
	int arr[6] = {1, -1 ,1 ,1 -1 ,1};
	DrawTest(dstImage, index, arr);
}
void DrawTailDigeut(IplImage *dstImage,int index){
	int arr[6] = {1, 1, -1 ,1 -1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawTailRieul(IplImage *dstImage,int index){
	int arr[6] = {1, -1, 1, 1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawTailMieum(IplImage *dstImage,int index){
	int arr[6] = {1, -1, 1 ,1, 1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawTailBieup(IplImage *dstImage,int index){
	int arr[6] = {-1, -1, 1, 1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawTailSiot(IplImage *dstImage,int index){
	int arr[6] = {1, 1, -1, 1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawTailIeung(IplImage *dstImage,int index){
	int arr[6] = {1, -1, -1, 1, -1 ,-1};
	DrawTest(dstImage, index, arr);
}
void DrawTailJieut(IplImage *dstImage,int index){
	int arr[6] = {-1, 1, -1, 1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawTailChieut(IplImage *dstImage,int index){
	int arr[6] = {1, -1 ,-1, 1, 1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawTailKieuk(IplImage *dstImage,int index){
	int arr[6] = {1, -1, -1, 1, -1, 1};
	DrawTest(dstImage, index, arr);
}
void DrawTailTieut(IplImage *dstImage,int index){
	int arr[6] = {1, -1, -1, 1, 1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawTailPieup(IplImage *dstImage,int index){
	int arr[6] = {1, -1, 1, 1, -1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawTailHieut(IplImage *dstImage,int index){
	int arr[6] = {1, 1, -1, 1 ,-1, -1};
	DrawTest(dstImage, index, arr);
}
void DrawTailDoubleSiot(IplImage *dstImage,int index){
	int arr[6] = {1, 1, -1, -1, 1, 1};
	DrawTest(dstImage, index, arr);
}



/////===================숫자========================
void DrawNumOne(IplImage *dstImage,int index){
	int arr[6] = {-1,1,1,1,1,1};
	DrawTest(dstImage, index, arr);
}
void DrawNumTwo(IplImage *dstImage,int index){
	int arr[6] = {-1,-1,1,1,1,1};
	DrawTest(dstImage, index, arr);
}
void DrawNumThree(IplImage *dstImage,int index){
	int arr[6] = {-1,1,1,-1,1,1};
	DrawTest(dstImage, index, arr);
}
void DrawNumFour(IplImage *dstImage,int index){
	int arr[6] = {-1,1,1,-1,-1,1};
	DrawTest(dstImage, index, arr);
}
void DrawNumFive(IplImage *dstImage,int index){
	int arr[6] = {-1,1,1,1,-1,1};
	DrawTest(dstImage, index, arr);
}
void DrawNumSix(IplImage *dstImage,int index){
	int arr[6] = {-1,-1,1,-1,1,1};
	DrawTest(dstImage, index, arr);
}
void DrawNumSeven(IplImage *dstImage,int index){
	int arr[6] = {-1,-1,1,-1,-1,1};
	DrawTest(dstImage, index, arr);
}
void DrawNumEight(IplImage *dstImage,int index){
	int arr[6] = {-1,-1,1,1,-1,1};
	DrawTest(dstImage, index, arr);
}
void DrawNumNine(IplImage *dstImage,int index){
	int arr[6] = {1,-1,1,-1,1,1};
	DrawTest(dstImage, index, arr);
}
void DrawNumZero(IplImage *dstImage,int index){
	int arr[6] = {1,-1,1,-1,-1,1};
	DrawTest(dstImage, index, arr);
}