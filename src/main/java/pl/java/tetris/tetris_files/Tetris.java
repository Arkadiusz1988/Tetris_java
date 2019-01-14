package pl.java.tetris.tetris_files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.java.tetris.entities.Game_Results;
import pl.java.tetris.repositories.GameResultRepository;

import javax.swing.*;
import java.awt.*;

import static pl.java.tetris.tetris_files.Table.gameResults;
import static pl.java.tetris.tetris_files.Table.isGameRun;

@SpringBootApplication
@ComponentScan(basePackages = "pl.java.tetris")
@RestController
public class Tetris extends JFrame {

//    @Autowired
//    public GameResultRepository gameResultRepository;

    private JLabel statusbar;

    public Tetris() {

        initUI();
    }


    private void initUI() {

        statusbar = new JLabel(" 0");
        add(statusbar, BorderLayout.SOUTH);

        Table table = new Table(this);
        add(table);
        table.start();

        setTitle("Tetris");
        setSize(200, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

     //   if(board.tryMove());
    }

    public JLabel getStatusBar() {

        return statusbar;
    }

  @GetMapping(value = "/start")
  public void start() {

       // main(null);
     // EventQueue.invokeLater(() -> {
//
          Tetris game = new Tetris();
          game.setVisible(true);
 //         if(game.is){
//             Game_Results gameRe = new Game_Results();
//             gameRe.setResult(String.valueOf(gameResults));
//             gameResultRepository.save(gameRe);
//          }
//          if(statusbar.getText().equals("Game over")){
//
//          }
     // });
  //  System.out.println("cos");


  }

        public static void main(String[] args) {
            SpringApplication.run(Tetris.class, args);
          //  void start1(){
//            ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Tetris.class)
//                    .headless(false).run(args);

//            EventQueue.invokeLater(() -> {
//                Tetris ex = ctx.getBean(Tetris.class);
//                ex.setVisible(true);
//            });
    }
}
