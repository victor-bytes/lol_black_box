import com.qq.lol.dto.LolClientDto;
import com.qq.lol.utils.NetRequestUtil;
import com.qq.lol.utils.ProcessUtil;

import java.io.IOException;

/**
 * @Auther: zzm
 * @Date: 2023/11/29 - 11 - 29 - 14:19
 * @Description: TODO
 * @version: 1.0
 */
public class TestNetRequestUtil {
    public static void main(String[] args) throws IOException {
        LolClientDto riotClientDto = ProcessUtil.getClientProcess();
        NetRequestUtil requestUtil = NetRequestUtil.getNetRequestUtil();
        String s = requestUtil.doGet("/lol-summoner/v1/current-summoner");
        System.out.println("-------------TestNetRequestUtil-------------------");
        System.out.println(s);
        System.out.println("-------------TestNetRequestUtil-------------------");


//            # print(f"summonerName:    {data['displayName']}")
//    # print(f"gameName:    {data['gameName']}")
//    # print(f"summonerLevel:   {data['summonerLevel']}")
//    # print(f"profileIconId:   {data['profileIconId']}")
//    # print(f"summonerId:      {data['summonerId']}")
//    # print(f"puuid:           {data['puuid']}")
    }
}
