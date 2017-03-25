#include "opencv2/highgui/highgui.hpp"


typedef struct BrailleModel {
	unsigned int ModelTag;
	unsigned int ModelFlag;
}ModelBraille;



void DrawTest(IplImage *dstImage,int index, int arr[]);
//void BrailleCheckFunc(IplImage *dstImage, const wchar_t *const ParsingHangle, Queue *bQueue);

//// ============ �ʼ� ==============
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


//// ============ �߼� ==============
void DrawMidA(IplImage *dstImage,int index);	// '��'
void DrawMidYa(IplImage *dstImage, int index);	// '��'
void DrawMidEo(IplImage *dstImage,int index);	// '��'
void DrawMidYeo(IplImage *dstImage,int index);	// '��'
void DrawMidO(IplImage *dstImage,int index);	// '��'	
void DrawMidYo(IplImage *dstImage,int index);	// '��'
void DrawMidU(IplImage *dstImage,int index);	// '��'
void DrawMidYu(IplImage *dstImage,int index);	// '��'
void DrawMidEu(IplImage *dstImage,int index);	// '��'
void DrawMidI(IplImage *dstImage,int index);	// '��'	
void DrawMidAe(IplImage *dstImage,int index);	// '��'
void DrawMidE(IplImage *dstImage,int index);	// '��'
void DrawMidOe(IplImage *dstImage,int index);	// '��'
void DrawMidOa(IplImage *dstImage,int index);	// '��'
void DrawMidUeo(IplImage *dstImage,int index);	// '��'
void DrawMidEui(IplImage *dstImage,int index);	// '��'
void DrawMidYeoi(IplImage *dstImage,int index);	// '��'


//// ============ ���� ==============
void DrawTailBlank(IplImage *dstImage, int index);	// ����
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



//// ============ ���� ==============
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