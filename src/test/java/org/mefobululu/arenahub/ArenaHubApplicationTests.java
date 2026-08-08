package org.mefobululu.arenahub;

import org.junit.jupiter.api.Test;
import org.mefobululu.arenahub.mapper.PlayerMapper;
import org.mefobululu.arenahub.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootTest
class ArenaHubApplicationTests {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private PlayerMapper playerMapper;

    @Test
    void contextLoads() throws Exception {
        try(Connection connection = dataSource.getConnection()){
            System.out.println(connection);
        }
    }
    @Test
    void findPlayerId(){
        Player player = playerMapper.findById(1L);

        System.out.println(player.getId());
        System.out.println(player.getNickname());
        System.out.println(player.getLevel());
    }

}
