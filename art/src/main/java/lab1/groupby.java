package lab1;

public interface groupby {
    DataFrame max() ;
    DataFrame min() ;
    DataFrame mean();
    DataFrame std() ;
    DataFrame sum();
    DataFrame var() ;
    DataFrame apply(Applyable operation);
}