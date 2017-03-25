#include <stdio.h>
#include "BrailleModel.h"

typedef struct node {
    ModelBraille item;
    struct node *next;
} Node;

typedef struct queue {
    Node *front;
    Node *rear;
	int nCount;
    
} Queue;

static void CopyToNode(ModelBraille item, Node *pn);
static void CopyToItem(Node *pn, ModelBraille *pi);
 
void InitializeQueue(Queue *pq);
bool QueueIsFull(const Queue *pq);
bool QueueIsEmpty(const Queue *pq);
int QueueitemCount(const Queue *pq);
bool addQueue(ModelBraille item, Queue *pq);
bool deleteQueue(ModelBraille *pTemp, Queue *pq);