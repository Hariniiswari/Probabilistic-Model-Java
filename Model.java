import java.util.*;

public class Model 
{
	public static void main(String[] args) 
	{
		Scanner input = new Scanner(System.in);
		int relevant_doc, docs, query, temp, count = 0;
		
		System.out.print("Enter the number of documents : ");
		docs = input.nextInt();
		
		System.out.print("Enter the number of query : ");
		query = input.nextInt();
		
		int [][] doc_matrix = new int[docs+1][query+1];
		
		System.out.println("Enter the elements of matrix:- ");
		for(int i=0;i<docs;i++)
		{
			for(int j=0;j<query;j++)
			{
				doc_matrix[i][j] = input.nextInt();
			}
		}
		
		System.out.print("Enter the number of relevant documents : ");
		relevant_doc = input.nextInt();
		
		int [] rel_doc = new int [docs+1];
		
		for(int i=1;i<=docs;i++)
		{
			rel_doc[i] = 0;
		}
		
		System.out.println("Enter the relevant document number");
		for(int i=1;i<=relevant_doc;i++)
		{
			temp = input.nextInt();
			rel_doc[temp-1] = 1;
		}
		
		int non_rel_doc_no = docs - relevant_doc;
		
		System.out.println("=========================================");
		System.out.println("Total documents : " + docs);
		System.out.println("=========================================");
		System.out.println("Total query : " + query);
		System.out.println("=========================================");
		System.out.println("The matrix form of documents and query:-");
		int row = docs;
		int cols = query;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<cols;j++)
			{
				System.out.print(doc_matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("=========================================");
		System.out.println("The number of relevant documents : " + relevant_doc);
		System.out.println("=========================================");
		System.out.println("The relevant documents are : ");
		for(int i=0;i<docs;i++)
		{
			if(rel_doc[i] == 1)
			{
				temp = i+1;
				System.out.println("Document " + temp);			
			}
		}
		System.out.println("=========================================");
		System.out.println("The number of non-relevant documents : " + non_rel_doc_no);
		System.out.println("=========================================");
		System.out.println("The non-relevant documents are : ");
		for(int i=0;i<docs;i++)
		{
			if(rel_doc[i] == 0)
			{
				temp = i+1;
				System.out.println("Document " + temp);			
			}
		}
		
		float rel_prob = (float) relevant_doc / docs;
		System.out.println("=========================================");
		System.out.println("Probability of relevant documents P(R) : " + rel_prob);
		System.out.println("=========================================");
		
		float non_rel_prob = (float) non_rel_doc_no / docs;	
		System.out.println("=========================================");
		System.out.println("Probability of non-relevant documents P(R') : " + non_rel_prob);
		System.out.println("=========================================");
		
		float rel_non_rel_prob = (float) rel_prob / non_rel_prob;	
		System.out.println("=========================================");
		System.out.println("Probability of non-relevant documents P(R/R') : " + rel_non_rel_prob);
		System.out.println("=========================================");
		
		float [] q_rel = new float[query+1];
		float [] q_non_rel = new float[query+1];
		
		for(int i=0;i<query;i++)
		{
			count = 0;
			for(int j=0;j<docs;j++)
			{
				if(rel_doc[j] == 1)
				{
					if(doc_matrix[j][i] == 1)
					{
						count++;
					}
				}
			}
			q_rel[i] = count;
			q_rel[i] = q_rel[i] / relevant_doc;
			temp = i+1;
			System.out.println("P(K" + temp + "/R) = " + q_rel[i]);
		}
		System.out.println();
		for(int i=0;i<query;i++)
		{
			count = 0;
			for(int j=0;j<docs;j++)
			{
				if(rel_doc[j] == 0)
				{
					if(doc_matrix[j][i] == 1)
					{
						count++;
					}
				}
			}
			q_non_rel[i] = count;
			q_non_rel[i] = q_non_rel[i] / non_rel_doc_no;
			temp = i+1;
			System.out.println("P(K" + temp + "/R') = " + q_non_rel[i]);
		}
		
		float [] sim = new float[docs+1];
		int [] rank = new int[docs+1];
		
		for(int i=0;i<docs;i++)
		{
			sim[i] = 1;
		}
				
		for(int i=0;i<docs;i++)
		{
			for(int j=0;j<query;j++)
			{
				if(doc_matrix[i][j] == 1)
				{
					sim[i] = sim[i] * (q_rel[j] / q_non_rel[j]) * rel_non_rel_prob;
				}
			}
		}
		
		System.out.println("=========================================");
		System.out.println("The similarity values for each document: -");
		System.out.println("=========================================");
		for(int i=0;i<docs;i++)
		{
			temp = i + 1;
			System.out.println("sim(d" + temp + ", q) = " + sim[i]);
		}
		
		for(int i=0;i<docs;i++)
		{
			count = 0;
			for(int j=0;j<docs;j++)
			{
				if(sim[j] >= sim[i])
				{
					count++;
				}
			}
			rank[i] = count;
		}
		
		for(int i=0;i<docs;i++)
		{
			for(int j=i+1;j<docs;j++)
			{
				if(rank[i] == rank[j])
				{
					rank[i] = rank[i] - 1;
				}
			}
		}
		
		System.out.println("=========================================");
		System.out.println("Rank for each document: -");
		System.out.println("=========================================");
		for(int i=0;i<docs;i++)
		{
			temp = i + 1;
			System.out.println("sim(d" + temp + ", q) = " + sim[i] + "  || Rank : " + rank[i]);
		}
		
		System.out.println("=========================================");
		System.out.println("FINAL SOLUTION: -");
		System.out.println("=========================================");
		System.out.println("Threshold value = 0");
		System.out.println("=========================================");
		System.out.println("RELEVANT DOCUMENTS: -");
		System.out.println("=========================================");
		for(int i=0;i<docs;i++)
		{
			if(sim[i] >= 1)
			{
				temp = i + 1;
				System.out.println("sim(d" + temp + ", q) = " + sim[i] + "  || Rank : " + rank[i]);
			}
		}
		System.out.println("=========================================");
		System.out.println("NON-RELEVANT DOCUMENTS: -");
		System.out.println("=========================================");
		for(int i=0;i<docs;i++)
		{
			if(sim[i] < 1)
			{
				temp = i + 1;
				System.out.println("sim(d" + temp + ", q) = " + sim[i] + "  || Rank : " + rank[i]);
			}
		}
	}
}