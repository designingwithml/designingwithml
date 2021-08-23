## Extractive Summarization with BERT 

![alt text](images/inference.png)

In this approach, we treat extractive summarization as a classification problem where we predict a class for each sentence in a document (i.e. belonging to the summary or not). 

- Model Input: a sentence, and the entire document 
- Model Output: A score representing likelihood of belonging in the summary or not.   


### Model Implementation 

![alt text](images/extractivesummodel.png)

- We use sentence bert models to get representations for our text. We use the smallest model (sentence-transformers/paraphrase-MiniLM-L3-v2). 
Note that this can be replaced by a larger more accurate model. See the list of sentence bert pretrained [models](https://www.sbert.net/docs/pretrained_models.html#sentence-embedding-models).
- Representations for sentence and document are concatenated and fed to a Dense layer and then prediction output. 
- Entire model is finetuned on the CNN/Dailymail dataset. 