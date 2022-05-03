### PROGRAM DESCRIPTION:

Your backend system receives a monthly account statement from a third party system and stores it into a nosql db  collection.  
The db collection contains all bank transactions; each transaction has a date, amount and the expense category.  

Sample csv format of collection:  
`2021-11-28,-3200.5,Travel`  
`2021-11-28,-1740.6,Online Shopping`  
`2021-11-25,-2800.3,Grocery`  
`2021-11-25,-4000,Housing Loan`  
`2021-11-20,1200,Stock Trading`  
`2021-11-17,-200,Online Shopping`  
`2021-11-15,-2600.60,Grocery`  
`2021-11-10,-1500.99,Grocery`  
`2021-11-05,20000,Salary`  



### TASK 1:

Your backend system has recently migrated from azure cosmosdb to mongodb atlas serverless.  
Make the necessary changes on your application to boot-up successfully.  



### TASK 2:

Your client requested a new report. Develop an api to generate a simple json response as below:

`GET http://localhost:8080/api/transaction-summary?startDate=2021-11-01&endDate=2021-11-30`  
{  
"total_income": 21200.00,  
"total_expenses": 16042.99,  
"total_savings": 5157.01,  
"top_expense_category": "6901.89 @Grocery"  
}



### TASK 3:

Your colleague is developing another api which conflicts with your changes.  
Resolve the merge conflict, commit, then push.  
