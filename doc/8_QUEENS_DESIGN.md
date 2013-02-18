DESIGN & IMPLEMENTATION NOTES
=============================


Chromosomes
--------------

For each queen only need to represent the y as a value from 0 - 7 using 3 bits.
Only the y value is need as you can ONLY have one queen per row. Therefore the
order of each Y value in the chromosome is the order of the rows.

Each gene shows the position (x) of each queen in one row based on the order and
the y value of the queen is stored in the gene of the chromosome, rather than having 
each queen represented as x & y.

The following is an example of a chromose with the 8 queens represented as genes
of the chromosome.

    ```
    -----------------------------
    | Q1 | Q2 | Q3 | ..... | Q8 |
    -----------------------------
    ```



Fitness Fuction
----------------

The purpose of the fitness function is to minimize the number of collisions of
each queen when you are adding up all of the collisions (summation). If you 
have no collisions then you have found a solution to a problem.

Collisions can occur in three ways:

1.  Horizontal collision, when there are two queens on the same row, this is the
    trivial case and does not occur because the ordering of the queens in the 
    chromosome represents each row.

2.  Vertical colision, this occurs if two queens occupy the same column on the
    chessboard.

3.  Diagonal collision, this occurs if there is a collision on the diagonal you
    calculate the slope between the two queens if it has a slope of abs| 1 | then 
    there is a diagonal collision.


## Algorithm Steps

1.  For each queen (gene) in the chromosome calculate the number of vertical
    collisions and diagonal collisions, the horizontal case does not occur do
    to the ordering of queens (genes) in the chromosome.

    ie. Q1 = 2
        Q2 = 5

    Repeated collisions are still counted so if Q1 collides with Q2 they would EACH
    have one collision marked down (both Q1 and Q2). This is to keep consistency
    such that it is easy to calculate the total number of collisions relative to
    each queen.

2.  For each chromosome get the summation of each of the collisions for each queen
    in the chromosome.

3.  Repeat steps 1 & 2 for each chromosome in the population

4.  Calculate the fitness of each chromosome using the following fitness function


    fitness = 1 - (chromosome collision) / (sum all chromosomes collisions)


5.  Each of the fitness values is a float of the fitness of the chromosome, stop
    the GA if there is a chromosome with a fitness of 1, this means that the
    chromosome has a "full fitness" and thus no collisions.



Selection Method
------------------

1.  Use the fitness value of each of the chromosomes to create a range of values
    from 0 -> # Queens - 1 where each queen is [start, end) except for the last
    queen.

    For example if Q1 = 0.4, Q2 = 0.3, Q3 = 0.6

    Q1 = [0, 0.4), Q2 = [0.4, 0.7), Q3 = [0.7, 1.3]

2.  Next, use a PRNG to generate a random double value % (#queens - 1) in order
    to select the chromosomes using roulette wheel selection.




Cloning, Crossover, Mutation
-----------------------------

After the selection has been made for the next population the choice of whether
or not to clone, crossover, or mutate is made. The probabilities of each are 
below. The three operations represent a range of values out of 100, a random number
between 0 - 99 is selected and if it lies within the range of the operation then
that operation is applied.

Cloning = 0.3     [0, 29]
Crossover = 0.69  [30, 98]
Mutation = 0.01   [99]


## Cloning

In cloning the chromosome is simply cloned/copied to next population.


## Crossover

Two chromosomes are selected and a random number < 8 is selected to determine
the point where the two chromosomes are crossed over to create two new offspring.


## Mutation

A random gene (queen) is selected and the value is randomly changed to a new
value between [0, 7]


