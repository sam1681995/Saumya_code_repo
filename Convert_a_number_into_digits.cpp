//Split a number into digits
#include<iostream>
#include<conio.h>
using namespace std;
int main()
{
int amt;
int rem[1000];
int i=0;
cout<<"\nEnter amount : ";
cin>>amt;
while(amt != 0)
{
          rem[i]=amt%10;
          amt=amt/10;
          i++;
}
int n=i-1;
while(n>=0)
{
           cout<<rem[n];
           n--;
}
 getch();
 return 0;   
}
