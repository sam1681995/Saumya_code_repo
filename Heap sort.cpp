//Program for heap sort
#include<iostream>
#include<conio.h>
using namespace std;
void swap(int *a,int *b)
{
int t;
t=*a;
*a=*b;
*b=t; 
}
void minheapify(int a[],int i,int n)
{
int lchild=2*i;
int rchild=2*i+1;
int min;
if(lchild<=n && a[lchild]<a[i])
min=lchild;
else
min=i;
if(rchild<=n && a[rchild]<a[min])
min=rchild; 
if(min!=i)
{
swap(&a[min],&a[i]);
minheapify(a,min,n);
}   
}
void buildheap(int a[],int n)
{
     for(int i=n/2;i>=1;i--)
     minheapify(a,i,n);
}
void heapsort(int a[],int n)
{
buildheap(a,n);
//Make the rightmost element as root and reduce heap size by 1
for(int j=n;j>=2;j--)
{
        swap(&a[j],&a[1]);
        minheapify(a,1,j-1);
}     
}
int main()
{
    int a[50];
    int n;
    cout<<"\nHow many elements do you want to sort : ";
    cin>>n;
    cout<<"\nEnter the elements : \n";
    for(int i=1;i<=n;i++)
    cin>>a[i];
    heapsort(a,n);
    cout<<"\nThe sorted array is : \n";
    for(int k=n;k>=1;k--)
    cout<<a[k]<<" ";
    getch();
    return 0;
}
