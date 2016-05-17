/*
middle element of linked list
concept of fast and slow pointers ,slow pointer moves 1 node at a time and fast pointer moves 2 nodes at a time
*/
#include<iostream>
#include<conio.h>
using namespace std;
class node
{
       public:
       int data;
       node *next;
       node(int num)
       {
       data=num;
       next=NULL;
       }
       friend class list;
};
class list
{
       public:
      node *head;
      list()
      {
            head=NULL;
      }
      void insert();
      node* middle_element();
      void display();
};  
void list::insert()
{
     int n,num,ans;
     node *temp,*newnode;
     do{
     cout<<"\nEnter the data of node : ";
     cin>>num;
     newnode=new node(num);
     //newnode->data=num;
if(head==NULL)
{
  head=newnode;            
}
else
{
    temp=head;
    while(temp->next != NULL)
    {
            temp=temp->next;         
    }
   temp->next=newnode; 
}
cout<<"\nAny more nodes : y : 1 or n : 0 : ";
cin>>ans;  
}while(ans==1);
 }  
 
 void list::display()
{
  node *temp;   
  int ans;
if(head==NULL)
{
 cout<<"\nEmpty list !!!\n";           
}
else
{
    temp=head;
    while(temp!=NULL)
    {
    cout<<temp->data<<"-->";
    temp=temp->next;         
    }
    cout<<"NULL";
  
}

 }  
  
node* list:: middle_element()
{
      node *slow;
      node *fast;
      if(head==NULL)
      {
                    return NULL;
      }
       slow=head;
       fast=head->next;
       while(fast!=NULL && fast->next!=NULL)
       {
               slow=slow->next;
               fast=fast->next->next;         
       }   
      return slow;
}
int main()
{
    list l;
    l.insert();
    l.display();
    //l.middle_element();
    cout<<"\nMiddle element : "<<l.middle_element()->data;
    getch();
    return 0;
}
