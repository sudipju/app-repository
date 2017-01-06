package com.iprofile.assets.domain.cna;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CorporateNodeAccountRepository extends PagingAndSortingRepository<CorporateNodeAccount, String>{

	// start person=node:Person(id={0}) return person
	CorporateNodeAccount findByCorporateNodeId(Integer corporateNodeId);
	
	// start person=node:Person({0}) return person - {0} will be "id:"+name
		//    Iterable<Person> findByNameLike(String name)

		    // start person=node:__types__("className"="com...Person")
		    // where person.age = {0} and person.married = {1}
		    // return person
		  //  Iterable<Person> findByAgeAndMarried(int age, boolean married)
    
}
