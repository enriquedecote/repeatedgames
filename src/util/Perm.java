/*******************************************************************************
 * Copyright (c) 2011 Enrique Munoz de Cote.
 * repeatedgames is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * repeatedgames is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with repeatedgames.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Please send an email to: jemc@inaoep.mx for comments or to become part of this project.
 * Contributors:
 *     Enrique Munoz de Cote - initial API and implementation
 ******************************************************************************/
package util;

import java.util.Vector;

/*
 *SOURCE: http://www.velocityreviews.com/forums/t391066-permutation-with-repetition.html
 */
// used to JointActionState, to generate all joint actions with repetition
class Perm{
	int n,numPlayers,count,size=4,RHS;
	Vector<Vector<Integer>> fullArray;
	Vector<Integer> array;
	int m =0;

	int charList[]=new int[10];
	int chrs[]={0, 1, 2, 3};
	int arr[]={0, 1, 2, 3};

	public Perm(int players){
		numPlayers=players;
		fullArray = new Vector<Vector<Integer>>();	
	}


	void swap(int []a, int i, int j){
		int temp;
		temp=a[i];
		a[i]=a[j];
		a[j]=temp;
	}

	Vector<Vector<Integer>> permute(int start){
		int i,j;
		m++;
		if(start==numPlayers){
			array = new Vector<Integer>();
			for(i=0;i<numPlayers;i++){

				System.out.print("a" +charList[i]);
				array.add(charList[i]);
				System.out.println();
				/System.out.println(array);

			}fullArray.add(array);
		}
		
		else{
			i=start;
			int k=0;

			for(j=0;j<chrs.length;j++){
				charList[i]=chrs[k++];
				permute(start+1);
			}
		}

		return fullArray;
	}
	void permute2(int i){
		int j=0,k=0;
		int temp;
		if(i == size){
			return;
		}
		for(j = i; j < size; j++){
			swap(arr,i,j);
			permute2(i+1);
			if(i == size - 1){
				System.out.print("Permute["+count+"]=");
				// for(k = 0;k< size;k++)
				System.out.print(arr);
				System.out.println();
				swap(arr,j,i);
			}
			temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}
	}
}
