class Model{//fetch and store all data from server and also have the functions that the UI uses to access the data. 
		//private static Model _instance;
		private static Model instance(){
			if(_instance == null){
				_instance = new Model();
			}
		}
		public static void setUser(Person person){
			instance()._setUser(person);
		}
		getUser
		hasUser//if user is logged in.
		
		addPerson
		addEvent
		
		//settings stored here
		
		
		//we want to make sure the app is using one Model. one way to do this is a singleton class. 
				//make constructor private.
				//
	
}