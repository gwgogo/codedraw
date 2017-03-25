#include "opencv2/highgui/highgui.hpp"


typedef struct BrailleModel {
	unsigned int ModelTag;
	unsigned int ModelFlag;
}ModelBraille;



void DrawTest(IplImage *dstImage,int index, int arr[]);
//void BrailleCheckFunc(IplImage *dstImage, const wchar_t *const ParsingHangle, Queue *bQueue);

//// ============ 段失 ==============
void DrawHeadGiyeok(IplImage *dstImage,int index);
void DrawHeadNieun(IplImage *dstImage,int index);
void DrawHeadDigeut(IplImage *dstImage,int index);
void DrawHeadRieul(IplImage *dstImage,int index);
void DrawHeadMieum(IplImage *dstImage,int index);
void DrawHeadBieup(IplImage *dstImage,int index);
void DrawHeadSiot(IplImage *dstImage,int index);
void DrawHeadIeung(IplImage *dstImage,int index);
void DrawHeadJieut(IplImage *dstImage,int index);
void DrawHeadChieut(IplImage *dstImage,int index);
void DrawHeadKieuk(IplImage *dstImage,int index);
void DrawHeadTieut(IplImage *dstImage,int index);
void DrawHeadPieup(IplImage *dstImage,int index);
void DrawHeadHieut(IplImage *dstImage,int index);
void DrawHeadAccent(IplImage *dstImage,int index);


//// ============ 掻失 ==============
void DrawMidA(IplImage *dstImage,int index);	// 'た'
void DrawMidYa(IplImage *dstImage, int index);	// 'ち'
void DrawMidEo(IplImage *dstImage,int index);	// 'っ'
void DrawMidYeo(IplImage *dstImage,int index);	// 'づ'
void DrawMidO(IplImage *dstImage,int index);	// 'で'	
void DrawMidYo(IplImage *dstImage,int index);	// 'に'
void DrawMidU(IplImage *dstImage,int index);	// 'ぬ'
void DrawMidYu(IplImage *dstImage,int index);	// 'ば'
void DrawMidEu(IplImage *dstImage,int index);	// 'ぱ'
void DrawMidI(IplImage *dstImage,int index);	// 'び'	
void DrawMidAe(IplImage *dstImage,int index);	// 'だ'
void DrawMidE(IplImage *dstImage,int index);	// 'つ'
void DrawMidOe(IplImage *dstImage,int index);	// 'な'
void DrawMidOa(IplImage *dstImage,int index);	// 'と'
void DrawMidUeo(IplImage *dstImage,int index);	// 'ね'
void DrawMidEui(IplImage *dstImage,int index);	// 'ひ'
void DrawMidYeoi(IplImage *dstImage,int index);	// 'て'


//// ============ 曽失 ==============
void DrawTailBlank(IplImage *dstImage, int index);	// 因拷
void DrawTailGiyeok(IplImage *dstImage, int index);
void DrawTailNieun(IplImage *dstImage,int index);
void DrawTailDigeut(IplImage *dstImage,int index);
void DrawTailRieul(IplImage *dstImage,int index);
void DrawTailMieum(IplImage *dstImage,int index);
void DrawTailBieup(IplImage *dstImage,int index);
void DrawTailSiot(IplImage *dstImage,int index);
void DrawTailIeung(IplImage *dstImage,int index);
void DrawTailJieut(IplImage *dstImage,int index);
void DrawTailChieut(IplImage *dstImage,int index);
void DrawTailKieuk(IplImage *dstImage,int index);
void DrawTailTieut(IplImage *dstImage,int index);
void DrawTailPieup(IplImage *dstImage,int index);
void DrawTailHieut(IplImage *dstImage,int index);
void DrawTailDoubleSiot(IplImage *dstImage,int index);



//// ============ 収切 ==============
void DrawNumOne(IplImage *dstImage,int index);
void DrawNumTwo(IplImage *dstImage,int index);
void DrawNumThree(IplImage *dstImage,int index);
void DrawNumFour(IplImage *dstImage,int index);
void DrawNumFive(IplImage *dstImage,int index);
void DrawNumSix(IplImage *dstImage,int index);
void DrawNumSeven(IplImage *dstImage,int index);
void DrawNumEight(IplImage *dstImage,int index);
void DrawNumNine(IplImage *dstImage,int index);
void DrawNumZero(IplImage *dstImage,int index);