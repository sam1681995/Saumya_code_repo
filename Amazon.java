/*
 Jhon plays a game in which he throws a baseball at various blocks marked with a symbol so as to knock these out. A core is computed for each throw. The “last score” is the score of the previous throw (or 0, if there is no previous throw) and the total score is the sum of the scores of all the throws. The symbol on a block can be an integer, a sing or a letter. Each sing or letter represents a special rule as given below:

If a throw hits a block marked with an integer, the score for that throw is the value of that integer.
If a throw hits a block marked with an “X”, the score for that throw is double the last score.
If a throw hits a block marked with a “+”, the score for that throw is the sum of the last two scores.
If a throw hits a block marked with a “Z”, the last score is removed, as though the last throw never happened. Its value does not count towards the total score, and the subsequent throws will ignore it when computing their values (see example below).

Write an algorithm that computes the total score for a given list of ordered hits by john.

Input
The input to the function/method consists of two arguments. Blocks, representing a list of symbols and n, an integer representing the number of symbols in the list.

Output
Return an integer representing the total score for the given list of ordered hits.

Example
Input:
blocks=[5,-2,4,Z,X,9,+,+],n=8

Output:
27

Explanition:
After the block marked with 5 is hit, the current score is 5 and the total score is 5.
After the block marked with -2 is hit, the current score is -2 and the total score is 3.
After the block marked with 4 is hit, the current score is 4 and the total score is 7.
After the block marked with “Z” is hit, the previous throw never happened, so the total score goes back to 3.
After the block marked with “X” is hit, the current score is 2*-2=-4 and the total score is -1.(remember, throws after a Z ignore the removed score when computing their values).
After the block marked with 9 is hit, the current score is 9 and the total score is 8.
After the block marked with “+” is hit, the current score is -4+9=5 and the total score is 13.
After the block marked with “+” is hit, the current score is 9+5=14 and the total score is 27.
 */
package amazon;

/**
 *
 * @author Saumya
 */
public class Amazon {
 private int auxBeforeCurrentScore=0;
 private int beforeCurrentScore=0;
 private int currentScore=0;
 private int totalScore=0;
 
 public static void main(String args[])
 {
     Amazon a=new Amazon();
     String[] blocks={"5","-2","4","Z","X","9","+","+"};
     System.out.println(a.totalScore(blocks,8));
 }
 
    public int totalScore(String[] blocks,int n)
    {
        for(int i=0;i<blocks.length;i++)
        {
            if(isInteger(blocks[i]))
            {
                continue;
            }
            if(isX(blocks[i]))
            {
                continue;
            }
             if(isPlus(blocks[i]))
            {
                continue;
            }
             if(isZ(blocks[i]))
            {
                continue;
            }
            
        }
     return totalScore;   
    }
    private boolean isInteger(String block)
    {
       try
       {
           int auxCurrentScore=Integer.parseInt(block);
           auxBeforeCurrentScore=beforeCurrentScore;
           beforeCurrentScore=currentScore;
           currentScore=auxCurrentScore;
           totalScore+=currentScore; 
         return true;  
       }
       catch(Exception e)
       {
           return false;
       }
    }
    
    private boolean isX(String block)
    {
       if(block.equals("X"))
       {
           auxBeforeCurrentScore=beforeCurrentScore;
           beforeCurrentScore=currentScore;
          currentScore=2*currentScore;
          totalScore+=currentScore;
          
         return true;  
       }
       else
       {
           return false;
       }
    }
    
    private boolean isPlus(String block)
    {
        if(block.equals("+"))
       {
           auxBeforeCurrentScore=beforeCurrentScore;
           int auxCurrentScore=currentScore;
           currentScore=currentScore+beforeCurrentScore;
           beforeCurrentScore=auxCurrentScore;
           totalScore+=currentScore;
           return true;  
       }
       else
       {
           return false;
       }
    }
    
    private boolean isZ(String block)
    {
        if(block.equals("Z"))
       {
           totalScore=totalScore-currentScore;
          currentScore=beforeCurrentScore;
          beforeCurrentScore=auxBeforeCurrentScore;
           return true;  
       }
       else
       {
           return false;
       }
    }
    
}
