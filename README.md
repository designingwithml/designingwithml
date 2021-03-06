# Designining with ML  

Collection of code samples that illustrate how to design solutions using machine learning models.


## Concepts with Code

Machine learning can be harnessed in solving many real world problems.  
While the machine learning life cycle is broad (problem framing, data collection, model training, evaluation, deployment, monitoring), this repository will focus on a subset of these steps:

- Problem Framing: Frame problems as as machine learning tasks (e.g. classification, regression, representation learning, metric learning etc) 
- Data Requirement Framing: Define a set of input (features) and target (label) variables required to address the problem. Curate this data where available or setup collection infrastructure as needed.
- Evaluation Metric Definition: Define a set of evaluation metrics that ideally generalize beyond the ML task and translate to value for the underlying business problem. 
- Model Training and Evaluation: Some python code to implement all the steps above 
- End user experience: How do we build rich web/mobile interfaces that support end users.


## Table of Content

This repo is organized in folders where each folder corresponds to specific topics.

| Topic      | Description | Solution Architecture    |
| :---        |    :----   |          :--- |
| [Extractive summarization with BERT](/extractivesummarization)     | In extractive summarization, the task is to extract subsets (sentences) from a document that represent a valid summary. In this repo, we treat extractive summarization as a classification problem where we predict a class for each sentence in a document (i.e. belonging to the summary or not). We can then assemble a summary based on these scores e.g. the highest scoring sentences reorded by some pertinent criteria (e.g. order of appearance in document, grammatical correctness, etc).       | ![alt text](/extractivesummarization/images/inference.png) [Notebooks](/extractivesummarization/notebooks)   |
| -   | -        | -      |

 
