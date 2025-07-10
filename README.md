**Description**
This component was created as a POC to let the administrative and onboarding task of users for portal wabum faster, letting increase by 5 times the number of profiles that could be managed by a single operator.
This tool could be used either to identify faster if a picture was respecting the basic aspects of Wabum specifications on term of pictures that could be used.
Project was delivered as a compomnent that could be integrated with what at that time was used as infrastructure, which was Wildfly AS.
As it is designed to be managed using an EJB (at that time there was an early phase for microservices adoption) that was communicating with the main component.
This was letting the project to be decoupled from eventual failures of this component that is based, under the hood, on openCV API, which is written in C++ and may fail from time to time.
Its implementation was based even on a Pool to let the system do parallel sync calls and get back the answer when ready. 
 
**Credits**
Edoardo Barba, which participated with his skills to our program for new graduates with UniRoma3.
