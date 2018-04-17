package com.jahnelgroup.auctionapp.initializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@Component
@Slf4j
public class DatabaseInitializer {

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Value("classpath:acl-schema.sql")
    Resource aclSql;

    @Value("classpath:seed.sql")
    Resource dataSql;

    /**
     * Create the ACL tables.
     *
     * @throws SQLException
     * @throws IOException
     */
    @PostConstruct
    public void init() throws SQLException, IOException {
        executeSql(dataSql);
        executeSql(aclSql);
    }

    private void executeSql(Resource sql) throws IOException, SQLException {
        log.info("executing sql {}", sql.getFile().getAbsolutePath());
        ScriptUtils.executeSqlScript(dataSource.getConnection(), sql);
    }

}
