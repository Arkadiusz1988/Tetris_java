package pl.java.tetris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.java.tetris.tetris_files.Tetris;

import java.awt.*;

@SpringBootApplication
public class TetrisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TetrisApplication.class, args);
       // ConfigurableApplicationContext context = new SpringApplicationBuilder(TetrisApplication.class).headless(false).run(args);
        //Tetris appFrame = context.getBean(Tetris.class);

   //     ConfigurableApplicationContext ctx = new SpringApplicationBuilder(TetrisApplication.class)
     //           .headless(true).run(args);
//
//        EventQueue.invokeLater(() -> {
  //          Tetris game = ctx.getBean(Tetris.class);
    //        game.setVisible(true);
      //  });
    }

}

