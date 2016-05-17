#include<iostream>
#include<conio.h>
#include<string.h>
#include<stdlib.h>
using namespace std;


void remove_duplicates(string arr[],int n)
{
 string arr1[n];
 int k=0;


 //sort the array
 std::sort(arr,arr+(n-1));
 
 //compare each element to its next
 for(int i=0;i<(n-1);i++)
 {
 
  if(arr[i] != arr[i+1])
  {
   arr1[k]=arr[i];
   k++;
  }
 }

 for(int i=0;i<k;i++)
 cout<<arr1[i]<<endl;
}


int main()
{
 string array[]={"Apple","Banana","Cherry","Grapes","Banana","Watermelon","Mango","Mango","Apple","Banana","Banana"};
 remove_duplicates(array,8);
 getch();
 return 0;   
}
