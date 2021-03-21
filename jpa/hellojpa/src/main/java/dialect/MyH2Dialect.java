package dialect;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;

import org.hibernate.type.StandardBasicTypes;


public class MyH2Dialect extends H2Dialect{
    public MyH2Dialect() {
        // H2Dialect 참조해서 StandardSQLFunction 만들기
        registerFunction("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
