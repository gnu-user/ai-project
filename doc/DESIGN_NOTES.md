Chromosome
-----------
- constructor that takes PRNG to generate chromosomes, used for creating init
  population

- constructor that takes int array to create cromosomes

get/set one chromosome
- get/set(index)

get/set a range of chromosomes
- get/set(start, end) 


Fitness Function
----------------

- abstract function that basically takes an array of chromosomes and returns 
  a fitness distribution (hashma

- calculate(chromosomes)


FitnessDist
------------

Basically a hash that has a range for the key and the value being that chromosome
object


Selection
----------

- abstract class that functions to take a fitness digest and returnes a new
  generation

- note could be used as an iterator with .next() to get each next chromosome
  for the next generation...



Notes
------

- Fitness function could be used to get the fitness value one at a time... which
  makes it possible to easily add optimal solutions to array

- stopping criteria is when 10 > UNIQUE solutions are found

- 

