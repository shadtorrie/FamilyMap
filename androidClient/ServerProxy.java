class ServerProxy{	//stands in the place of the server for the client.
	public static string serverHostName;
	public static int serverPortNumber;
	public LoginResult login(LoginRequest request){
		
		//validate request
		//serialize request to JSON
		//create http requests using HttpUrlConnection look at ticket to ride client.java
		//send http request
		//recieve http responds
		//convert responds to LoginResult
		
	}
	
	public PeopleListResult getAllPeople(peopleListResult request){
	
	}
	
	
}