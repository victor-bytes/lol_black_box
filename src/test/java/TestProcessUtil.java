import com.qq.lol.dto.LolClientDto;
import com.qq.lol.utils.ProcessUtil;

import java.io.IOException;

/**
 * @Auther: zzm
 * @Date: 2023/11/29 - 11 - 29 - 14:14
 * @Description: TODO
 * @version: 1.0
 */
public class TestProcessUtil {

    public static void main(String[] args) throws IOException {
        LolClientDto riotClientDto = ProcessUtil.getClientProcess();
    }
}
