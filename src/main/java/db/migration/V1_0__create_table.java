package db.migration;

import java.nio.charset.StandardCharsets;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.core.io.ClassPathResource;

public class V1_0__create_table extends BaseJavaMigration {

    @Override
    public void migrate(final Context context) throws Exception {
        final String sql = new String(
            new ClassPathResource("schema.sql").getInputStream().readAllBytes(),
            StandardCharsets.UTF_8);
        final var initStmt = context.getConnection().createStatement();
        initStmt.execute(sql);
    }
}
