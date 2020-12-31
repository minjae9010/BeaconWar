package tk.mjsv;

import ca.wacos.nametagedit.NametagAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.List;

public class Commands implements TabExecutor {
    //plugin prefix
    private static final String index = "§f[§bBeacon §cWars§f] ";
    private static main pl;
    public Commands(main plugin){
        Commands.pl=plugin;
    }

    //counter
    public static int seconds = 0;
    public static int Pseconds = 0;
    public static int Wseconds = 0;
    public static boolean TimerStop = false;
    public static String Tset = "없음";

    //팀 스코어보드 설정

    public static ScoreboardManager manager = Bukkit.getScoreboardManager();
    public static Scoreboard board = manager.getNewScoreboard();
    public static Team team1 = board.registerNewTeam("red");
    public static Team team2 = board.registerNewTeam("orange");
    public static Team team3 = board.registerNewTeam("yellow");
    public static Team team4 = board.registerNewTeam("green");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("남은시간")){
            switch (Timer.setting){
                case "전쟁":
                    sender.sendMessage(index+"남은 전쟁시간 : "+Timer.count/3600+"시간 "+Timer.count%3600/60+"분 "+Timer.count%60+"초");
                    break;
                case "평화":
                    sender.sendMessage(index+"남은 평화시간 : "+Timer.count/3600+"시간 "+Timer.count%3600/60+"분 "+Timer.count%60+"초");
                    break;
                default:
                    sender.sendMessage(index+"아직 설정되지 않았습니다");
            }
        }
        if (sender.isOp()){
            if (command.getName().equals("타이머")){
                if(args.length==1){
                    if(args[0].equals("설정")){
                        sender.sendMessage(index+"§e/"+label+" 설정 <내용> <분> <초>");
                    }
                    if(args[0].equals("시작")){
                        if(!(Pseconds==0)&!(Wseconds==0)) {
                            if(Timer.set){
                                sender.sendMessage(index+"이미 타이머가 시작되었습니다");
                            }
                            else {
                                sender.sendMessage(index + "타이머가 시작됩니다");
                                Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Timer(), 0,20);
                            }
                        }
                        else{
                            if(Pseconds==0){
                                sender.sendMessage(index+"평화시간이 설정되지 않았습니다.");
                            }
                            if(Wseconds==0){
                                sender.sendMessage(index+"전쟁시간이 설정되지 않았습니다.");
                            }
                        }
                    }
                    if(args[0].equals("종료")){
                        if (Timer.set) {
                            sender.sendMessage(index + "타이머가 곧 종료됩니다");
                            TimerStop = true;
                        }
                        else{
                            sender.sendMessage(index+"타이머가 시작되지 않았습니다");
                        }
                    }
                }
                else if(args.length>=2 & args.length<=6){
                    if(args[0].equals("설정")) {
                        if (args.length == 2) {
                            Tset = args[1];
                            sender.sendMessage(index + "시간을 정해주세요");
                        } else if (args.length == 3) {
                            Tset = args[1];
                            seconds = Integer.parseInt(args[2]) * 60;
                            sender.sendMessage(index + Tset + " " + seconds / 60 + "분 이 설정되었습니다");
                        } else if (args.length == 4) {
                            Tset = args[1];
                            seconds = Integer.parseInt(args[2]) * 60 + Integer.parseInt(args[3]);
                            sender.sendMessage(index + Tset + " " + seconds / 60 + "분 " + seconds % 60 + "초가 설정되었습니다");
                        }

                        }
                        if(!Tset.equals("없음"))
                            switch (Tset){
                                case "전쟁":
                                    Wseconds = seconds;
                                    break;
                                case "평화":
                                    Pseconds = seconds;
                                    break;
                                default:
                                    sender.sendMessage(index+"없는 타입입니다");
                            }
                    }
                else {
                    sender.sendMessage(index + "§e/" + label + " 설정 <타입> <분> <초>");
                    sender.sendMessage(index + "§e/" + label + " 시작");
                    sender.sendMessage(index + "§e/" + label + " 종료");
                }
            }

            if (command.getName().equals("팀")) {
                if (args.length == 1) {
                    if (args[0].equals("참가")) {
                        sender.sendMessage(index + "§e/" + label + " 참가 <팀이름> <플레이어>");
                    }
                    if (args[0].equals("목록")) {
                        sender.sendMessage(index + "§e/" + label + " 목록 <팀이름>");
                    }
                } else if (args.length == 2) {
                    if (args[0].equals("참가")) {
                        sender.sendMessage(index + "플레이어를 입력해주세요");
                    }
                    if (args[0].equals("목록")) {
                        sender.sendMessage(index + args[1] + "플레이어 목록");
                        switch (args[1]) {
                            case "red":
                                for (OfflinePlayer p : team1.getPlayers()) {
                                    sender.sendMessage(index + p.getName());
                                }
                                break;
                            case "orange":
                                for (OfflinePlayer p : team2.getPlayers()) {
                                    sender.sendMessage(index + p.getName());
                                }
                                break;
                            case "yellow":
                                for (OfflinePlayer p : team3.getPlayers()) {
                                    sender.sendMessage(index + p.getName());

                                }
                                break;
                            case "green":
                                for (OfflinePlayer p : team4.getPlayers()) {
                                    sender.sendMessage(index + p.getName());
                                }
                                break;
                        }
                    }
                } else if (args.length == 3) {
                    if (args[0].equals("참가")) {
                        Player p = Bukkit.getPlayer(args[2]);
                        boolean set = false;
                        if (p != null) {
                            switch (args[1]) {
                                case "red":
                                    team1.addPlayer(p);
                                    set = true;
                                    break;
                                case "orange":
                                    team2.addPlayer(p);
                                    set = true;
                                    break;
                                case "yellow":
                                    set = true;
                                    team3.addPlayer(p);
                                    break;
                                case "green":
                                    team4.addPlayer(p);
                                    set = true;
                                    break;
                            }
                            if (set) {
                                sender.sendMessage(index + p.getName() + "님이 " + args[1] + "에 들어가셨습니다.");
                                pl.getConfig().set("Playerinfo." + p.getName(), args[1]);
                                pl.saveConfig();
                                String prefix = pl.getConfig().getString(args[1]);
                                NametagAPI.setPrefix(p.getName(), prefix);
                                p.setDisplayName(prefix + p.getName());
                            } else {
                                sender.sendMessage(index + "팀 입력이 잘못되었습니다");
                            }
                        } else {
                            sender.sendMessage(index + "없거나 접속중이지 않는 플레이어 입니다.");
                        }
                    }
                }
            }
            if (command.getName().equals("공지")){
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(args.toString());
                Bukkit.broadcastMessage("");
            }
        }
        else{
            sender.sendMessage(index+"오피만 사용할 수 있습니다.");
        }
    return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (sender.isOp()) {
            if (command.getName().equals("타이머")) {
                if (args.length == 1) {
                    return Arrays.asList("설정", "시작", "종료");
                }
                if (args[0].equals("설정")){
                    switch (args.length){
                        case 2:
                            return Arrays.asList("평화","전쟁");
                        case 3:
                            return Arrays.asList("분");
                        case 4:
                            return Arrays.asList("초");
                    }
                }
            }
            if (command.getName().equals("팀")){
                if (args.length == 1){
                    return Arrays.asList("설정","참가","목록");
                }
                if(args.length == 2 & (args[0].equals("참가") | args[0].equals("목록"))){
                    return Arrays.asList("red","orange","yellow","green");
                }
            }
        }
        return null;
    }
}
