# Todo-App
## About the Installation of the Todo App
#### *** 1 Register and Sign Up:***
Fill in the Information that is required in Register and Sign Up.
#### *** 2 Login:***
After Filling up the register form and signing Up, Login with the same credentials.
##### ***3 Todo List:***
Click on the floating Add icon on the bottom right of the screen and create a task you want to track with its required infos
#### ***4 Todo Item:*** 
After creating a Todo Item, Now click on the create and Create your Todo Items.
#### ***5 Editing the Todo Items:***
Click on the Todo Item to update and edit it.
#### ***6 Deleting the todo items and Undo if you mistakenly deleted it ***.
You can swipe to delete the todo-list items and snackbar shows undo to undelete the item.
#### ***7 Deleting all List item***.
You can delete all the taks by clicking upon the menu option/more option near logout which will provide you delete all task options.
#### ***8 Logout Button***.
You can logout using the Logout button which will save all your tasks in your account only.


#### ***1 Register and Sign Up: : Fill the Required Information and Sign Up.
#### ***2 Login:*** : After Signing Up Login with the same credentials.
##### ***3 Todo List:*** : Click on the floating Add icon and create the Todo List
#### ***4 Todo Item:*** : After creating a Todo List ,Now click on the list and create your Todo Items.
#### ***5 Editing the Todo Items:*** : Click on the Todo Item and edit it.
#### ***6 Deleting the todo items and Undo if you mistakenly deleted it ***: You can delete Todolist or a todoItem.
#### ***7 Deleting all List item***. : You can delete all the tasks by this features.
#### ***8 Logout Button***: You can Logout and save your task in your account.

---

### Design Architecture 
# MODEL
* `adapter`  consists of recyclerviews adapters.<br>
-`SwipeLeftDelete`<br>
-`TodoListCallbacks`<br>
* `Database` consists of dao(class for room database) ,UserAuthentication,db..<br>
-`DataAccessObject`<br>
-`AppDatabase`<br>
-`Dateconvert`<br>
-`RegisterUserAuthentication`<br>
-`TodoLists`<br>


# `VIEW`
* `Data`  consists of all the datas i.e notification, session, task, user<br>
-`Screens` consists all the screens i.e addeditTask, login, menu, splash, tasks <br>



# `ViewModel`
* `LoginRegisterViewModel`  <br>
* `LogoutViewModel` <br>
* `AddEditTaskViewModel` <br>
* `SplashViewModel` <br>
* `DeleteAllTasksViewModel` <br>
* `MainActivityViewModel` <br>

## Features
Sign-IN                   |  Register                    | Adding Task             |  Adding Task SpeechToText
:----------------------------:|:--------------------------------------:|:----------------------:|:-----------------
<img src = "https://imgur.com/cYcAqLA.gif" width="200" height="360"> |<img src = "https://imgur.com/zIRnClB.gif" width="200" height="360">|<img src = "https://imgur.com/mEEDhYl.gif" width="200" height="360">|<img src = "https://imgur.com/tuuTJTn.gif" width="200" height="360">
 #
  
Undo         |  Menu-DeleteAllTask                  | Menu-ImplicitIntent|      Notificaton
:----------------------------:|:--------------------------------------:|:----------------------:|:-----------------
 <img src = "https://imgur.com/0VvfLRw.gif" width="200" height="360"> |   <img src = "https://imgur.com/Pnej4mW.gif" width="200" height="360">        | <img src = "https://imgur.com/PtVwwa8.gif" width="200" height="360">   | <img src = "https://imgur.com/zS8AUvE.gif" width="200" height="360">
 ---



----------------------------

## Documentation (Model–view–viewmodel architecture in a nutshell) 
Model–view–viewmodel (MVVM) is a software architectural pattern that facilitates the separation of the development of the graphical user interface (the view) be it via a markup language or GUI code from the development of the business logic or back-end logic (the model) so that the view is not dependent on any specific model platform. The view model of MVVM is a value converter,meaning the view model is responsible for exposing (converting) the data objects from the model in such a way that objects are easily managed and presented. In this respect, the view model is more model than view, and handles most if not all of the view's display logic.The view model may implement a mediator pattern, organizing access to the back-end logic around the set of use cases supported by the view.
#
![](https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/img/a7da8f5ea91bac52.png)

---

