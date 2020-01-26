One Item belongs to one and only one Category

One Category can contain zero or many Items

PricingPolicy: the code, name, description, creationDate, appliedStartDate and appliedEndDate are stocked in the
    database. How exactly the price is calculated for a given PricingPolicy is modeled in Java code.
    Why this choice?
    The PricingPolicy is more a process of calculating than a static data to be stocked, so its place is in the
    business layer and it is supposed to be changed regularly. SQL is a query language, very strong at querying data
    stocked in the tables of a relational database, but it is not for modeling a business process while Java is an
    object-oriented general purpose programming language, well designed for modeling business processes.
    By formulating the PricingPolicy using Java, we have multiple benefits:
     - We can unit test the process of calculating the price,
     - Business needs can be precisely formulated using test cases,
     - Creating and modifying a PricePolicy is made easy with Java

One Item can reference to zero or one PricingPolicy at a time.
If one Item references no PricingPolicy or to the "basic" PricingPolicy, so there is no re-calculation of price for this
Item.

