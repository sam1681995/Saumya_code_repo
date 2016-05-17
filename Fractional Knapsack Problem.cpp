//Fractional Knapsack Problem
#include <iostream>
#include <conio.h>
using namespace std;

int main()
{
	int n, M;				//n- No of items       M- Knapsack capacity
	cout<<"Number of items: ";
	cin>>n;
	cout<<"Maximum capacity of knapsack: ";
	cin>>M;
	int value[n], cost[n];
	float measure[n], wt=0, val=0;		//wt- Final weight	   val- Final value;
	for(int i=0;i<n;i++)
	{
		cout<<"Value and Cost of object "<<i+1<<": ";
		cin>>value[i]>>cost[i];
		measure[i]=value[i]/cost[i];
	}
	
	//Sort measure in descending order
	for(int i=0;i<n;i++)	//Bubble sort
	{
		for(int j=i;j<n;j++)
		{
			if(measure[i]>measure[j])
			{
				float temp=measure[i];
				measure[i]=measure[j];
				measure[j]=temp;
			}
		}
	}
	for(int i=0;i<n;i++)
		cout<<"\n Wt\Val "<<i+1<<" : "<<measure[i];
	
	for(int i=0;i<n;i++)
	{
		if((wt+cost[i])<M)
		{
			//Add object i to knapsack
			cout<<"\nAdd object "<<i+1<<" Value: "<<value[i]<<"   Weight: "<<cost[i];
			wt+=cost[i];
			val+=value[i];
		}
		else		//Add part of object
		{
			float WeightLeft=M-wt, ratio=WeightLeft/cost[i];
			wt+=ratio*cost[i];
			val+=ratio*value[i];
			cout<<"\nAdd object "<<i+1<<" Value: "<<ratio*value[i]<<" Weight: "<<ratio*cost[i];	
			break;	//Since no objects after this
		}
	}
	cout<<"\n\nFinal Value: "<<val<<"\nFinal Weight: "<<wt;

	getch();
	return 0;
}
