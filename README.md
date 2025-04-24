# API-Test-RestAssured-BDD
Automation API Testing using RestAssured with BDD Cucumber framework

## Test Application
[(https://petstore.swagger.io/)](https://petstore.swagger.io/)

## Tech Stack
1. IntelliJ - IDE
2. Java 1.8 - Programming language 
3. Rest Assured - API Automation library
4. jUnit - Test Management library
5. Cucumber - BDD

## Folder Structure
<img width="444" alt="Screenshot 2025-04-24 at 13 55 24" src="https://github.com/user-attachments/assets/998c7c0f-100d-44b5-adfb-1ac352c62d14" />

## Implemented Test Cases
### Positive Cases
1. Successfully Add New Pet To Store
2. Successfully Update Existing Pet Data
3. Successfully Find Pet By ID
4. Successfully Find Pet By Status
5. Successfully Delete Existing Pet Data

### Negative Cases
1. Find Pet With Unregistered ID
2. Find Pet With Invalid ID Format
3. Find Pet With Empty Parameter
4. Find Pet Status With Not Allowed Method
5. Add New Pet To Store With Invalid ID Format
6. Delete Pet Data With Unregistered ID

## Report
Once the execution completes report will be generated in below folder.<br>
**target/cucumber-reports/cucumber-pretty/index.html**

<img width="1200" alt="Screenshot 2025-04-24 at 12 35 10" src="https://github.com/user-attachments/assets/423232ed-ce96-4f9c-affd-80ae982083d0" />
<img width="1200" alt="Screenshot 2025-04-24 at 12 35 46" src="https://github.com/user-attachments/assets/833626d5-cff0-4441-8c3c-92b88746728b" />



