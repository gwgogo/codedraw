// TODO : 함수 포인터 재정의.
typedef int (*dWordAnalyze)(wchar_t *str, wchar_t *buffer, UINT nSize);
typedef void (*dInitBrailleQueue)(unsigned int count, const wchar_t *const ParsingHangle);
typedef void (*dDrawBraille)(unsigned int count, const wchar_t *const ParsingHangle);