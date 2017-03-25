#include <stdlib.h>
#include "BrailleQueue.h"

#define MAXQUEUE 1024

static void CopyToNode(ModelBraille item, Node *pn) {
    pn->item = item;
}
 
static void CopyToItem(Node *pn, ModelBraille *pi) {
    *pi = pn->item;
}
 
void InitializeQueue(Queue *pq) {
    pq->front = pq->rear = NULL;
    pq->nCount = 0;
}
 
bool QueueIsFull(const Queue *pq) {
    return pq->nCount == MAXQUEUE;
}
 
bool QueueIsEmpty(const Queue *pq) {
    return pq->nCount == 0;
}
 
int QueueitemCount(const Queue *pq) {
    return pq->nCount;
}
 
bool addQueue(ModelBraille item, Queue *pq) {
    Node *pnew;
 
    if (QueueIsFull(pq))
        return false;
    pnew = (Node *)malloc(sizeof(Node));
    if (pnew == NULL) {
        exit(1);
    }
    CopyToNode(item, pnew);
    pnew->next = NULL;
    if (QueueIsEmpty(pq))
        pq->front = pnew;
    else
        pq->rear->next = pnew;
    pq->rear = pnew;
    pq->nCount++;
 
    return true;
}
 
bool deleteQueue(ModelBraille *pTemp, Queue *pq) {
    Node *pt;
 
    if (QueueIsEmpty(pq))
        return false;
    CopyToItem(pq->front, pTemp);
    pt = pq->front;
    pq->front = pq->front->next;
    free(pt);
    pq->nCount--;
    if (pq->nCount == 0) {
        pq->front = pq->rear = NULL;
    }

    return true;
}