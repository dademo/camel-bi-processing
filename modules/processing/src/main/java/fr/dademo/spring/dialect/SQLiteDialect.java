package fr.dademo.spring.dialect;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.dialect.AnsiDialect;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SQLiteDialect extends AnsiDialect {

    public static final SQLiteDialect INSTANCE = new SQLiteDialect();
}
