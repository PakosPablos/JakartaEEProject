# Introduction

In this file i will disclose some of my thinking in creating the solution 
for the JakartaEE Project


# Database

For the 3 database tables i chose to stay simple and created movie, person and movie_actor

The structures of the the movie table looks like this:
<img width="740" height="228" alt="image" src="https://github.com/user-attachments/assets/83e93735-dcc7-47e3-9e9a-64374929d8e5" />


The structures of the the person table looks like this:
<img width="669" height="135" alt="image" src="https://github.com/user-attachments/assets/884df48a-e034-4ce4-848a-51a5fc84d8f3" />


And finally the structure of the movie_actor table looks like this:
<img width="638" height="129" alt="image" src="https://github.com/user-attachments/assets/00744f3b-226f-4877-9a4f-7c753b6f3be3" />


As for logic behind the tables:
Lets start with the movie_actor table, the primary key is compose of two primary keys, the movie_id and person_id, my reasoning behind that was that you woudl
need to have played in a movie to be a movie actor. I also mad that a movie_id had to match a movie.id and if that movie were to be deleted so all the actors connected to that movie in the table
And i did the same with the person.id, if a person were to be deleted, its actor linked part was also deleted

For the person table its kinda basic the idea behind it, it just holds the name and surname of that person, also the id is auto generate an increments auto maticaly by one if
another person is added

Lastly for the movie table i kept the same idea for the id, i also made it that the combination of the title and year should be unique to avoid any mixups and mistaken duplications.
Also made it that every director_id needs to reference a person.id.


Here below you can see a photo of a rought uml graph of the tables and there interactions.

<img width="482" height="432" alt="image" src="https://github.com/user-attachments/assets/b94eccab-eec3-4dc7-96a9-ef101566854a" />



The idea behind the movie_actor table was that every person with a direct conection to a movie would be a director, and every person listed
in the movie_actor table would be classified as an actor.


# General design idea and features

The idea of the features is basically that you can manage a small movie database through a few JSF pages. On the main page you just choose if you want to add data or search data. In the “add” part you can first create people (surname and name), 
then you can add movies where you pick one of those people as the director, and finally you can assign actors to movies by choosing a movie and a person and giving them a billing order. Each of those pages has the same layout: on the left you fill in a 
small form, and on the right you see the current list from the database update when you save something, so you can see right away that it worked. In the search part you can search movies by different criteria (title, year, genre, director name, actor name), 
and you also have simple searches that only show people who are directors or people who are actors, based on what is actually stored in the movie and movie_actor tables, not just everyone in person. 






