#include<iostream>
#include<conio.h>
#include<stdio.h>
#include<math.h>

using namespace std;

class node
{
	int data;
	node *next;
public:
	node(int n)
	{
		data = n;
		next = NULL;
	}
	friend class linkedlist;
};

class linkedlist
{
public:
	node *head;

	linkedlist()
	{
		head = NULL;
	}
	void display();
	void add_numbers_in_llist(node *head1,node *head2);
	void insert(int data);
};

void linkedlist::insert(int data)
{
	node *newnode = new node(data);
	if (head == NULL)
		head = newnode;
	else
	{
		node *temp = head;
		while (temp->next != NULL)
			temp = temp->next;
		temp->next = newnode;
		
	}
}
void linkedlist::display()
{
	cout << "\nThe contents of linked list are : " << endl;
	node *temp = head;
	while (temp != NULL)
	{
		cout << temp->data << endl;
		temp = temp->next;
	}
}
void linkedlist::add_numbers_in_llist(node *head1,node *head2)
{
	node *temp1 = head1;
	node *temp2 = head2;
	int num1[100], num2[100];
	int i = 0,j=0,k,l,sum1=0,sum2=0;
	while (temp1 != NULL)
	{
		num1[i] = temp1->data;
		temp1 = temp1->next;
		i++;
	}
	k = i;
	while (temp2 != NULL)
	{
		num2[j] = temp2->data;
		temp2 = temp2->next;
		j++;
	}
	l = j;
	while (i >= 0)
	{
		sum1 = sum1 + (num1[k-1-i] * pow(10, i));
		i--;
	}
	while (j >= 0)
	{
		sum2 = sum2 + (num2[l-1-j] * pow(10, j));
		j--;
	}
	cout << "\nTotal sum : " << sum1 + sum2 << endl;
}


void main()
{
	linkedlist l1, l2;
	l1.insert(0);
	l1.insert(5);
	l1.insert(6);
	l1.insert(3);
	//l1.insert(7);
	l1.display();
	l2.insert(0);
	//l2.insert(8);
	//l2.insert(4);
	//l2.insert(2);
	//l2.insert(6);
	l2.display();
	l1.add_numbers_in_llist(l1.head, l2.head);
	_getch();
}