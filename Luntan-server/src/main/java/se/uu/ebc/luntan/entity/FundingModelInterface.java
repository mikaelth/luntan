package se.uu.ebc.luntan.entity;


@FunctionalInterface
public interface FundingModelInterface {
    
    abstract Float computeFunding(Integer registerdStudents, Float ects, Integer baseLevel);

}
