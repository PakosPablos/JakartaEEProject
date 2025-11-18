# JakartaEEProject

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


Here below you can see a photo of the rought uml graph of the tables and there interactions.

<img width="482" height="432" alt="image" src="https://github.com/user-attachments/assets/b94eccab-eec3-4dc7-96a9-ef101566854a" />



