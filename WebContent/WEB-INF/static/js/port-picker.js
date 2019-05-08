/*!
 * =====================================================
 * SUI Mobile - http://m.sui.taobao.org/
 *
 * =====================================================
 */
// jshint ignore: start
+function($){

$.smConfig.rawCitiesData = [{"name":"广州","code":"11","sub":[{"name":"12","code":"1143"},{"name":"13","code":"1144"},{"name":"博罗","code":"113a"},{"name":"东江仓","code":"1124"},{"name":"二号码头","code":"1130"},{"name":"芳村","code":"1142"},{"name":"芳村内四港","code":"1103"},{"name":"番禺莲花山港","code":"1112"},{"name":"高要港北门","code":"112b"},{"name":"公益","code":"1120"},{"name":"广保通码头","code":"1111"},{"name":"广浚码头","code":"111f"},{"name":"广州","code":"1138"},{"name":"广州新港","code":"1131"},{"name":"桂平","code":"1128"},{"name":"海珠内二港","code":"1102"},{"name":"海珠内一港","code":"1101"},{"name":"宏兴码头","code":"1139"},{"name":"花都港","code":"1119"},{"name":"花都滘心港","code":"113c"},{"name":"黄埔","code":"1140"},{"name":"黄埔港","code":"112c"},{"name":"黄埔港北门","code":"112e"},{"name":"黄埔广裕码头","code":"110a"},{"name":"黄埔嘉利码头","code":"1105"},{"name":"黄埔建翔码头","code":"110b"},{"name":"黄埔集通码头","code":"1109"},{"name":"黄埔旧港大码头","code":"1129"},{"name":"黄埔旧港大码头/老港","code":"1107"},{"name":"黄埔庙头码头","code":"111a"},{"name":"黄埔乌冲码头","code":"1108"},{"name":"黄埔新港大码头","code":"111b"},{"name":"黄埔新港东江仓码头","code":"111d"},{"name":"黄埔新港广浚码头","code":"111e"},{"name":"黄埔新港全通秀丽码头","code":"110e"},{"name":"黄埔新港穗港码头","code":"110d"},{"name":"黄埔新港益海码头","code":"111c"},{"name":"黄埔新沙码头","code":"1110"},{"name":"黄埔永业码头","code":"1104"},{"name":"黄埔渔珠穗林码头","code":"1106"},{"name":"集司","code":"1125"},{"name":"旧南沙","code":"1135"},{"name":"开罗","code":"112d"},{"name":"快乐","code":"1126"},{"name":"啦啦啦","code":"112a"},{"name":"莲花山","code":"1121"},{"name":"李家莊","code":"113f"},{"name":"荔湾新风码头","code":"1100"},{"name":"龙溪","code":"1134"},{"name":"庙沙围码头","code":"1123"},{"name":"南沙","code":"1122"},{"name":"南沙东发货运码头","code":"1115"},{"name":"南沙港 ","code":"1117"},{"name":"南沙货运码头","code":"1116"},{"name":"南沙粮食通用码头","code":"1118"},{"name":"南沙南伟码头","code":"1114"},{"name":"南沙三期","code":"113b"},{"name":"南沙新港码头","code":"1113"},{"name":"南沙一期","code":"1133"},{"name":"沙头","code":"113e"},{"name":"市港澳码头","code":"110c"},{"name":"石龙","code":"1132"},{"name":"炭步","code":"1127"},{"name":"新港","code":"1141"},{"name":"新港集司码头","code":"110f"},{"name":"新塘口岸码头","code":"113d"},{"name":"鸦岗","code":"1137"},{"name":"一号码头","code":"112f"},{"name":"鱼珠","code":"1136"}]},{"name":"深圳","code":"12","sub":[{"name":"赤湾码头","code":"1202"},{"name":"翠湖码头","code":"1207"},{"name":"大铲湾港","code":"1203"},{"name":"东角头/亘富康信码头","code":"120c"},{"name":"东山码头","code":"1208"},{"name":"东涌码头","code":"1206"},{"name":"福永码头","code":"120a"},{"name":"海星港","code":"1209"},{"name":"凯丰码头","code":"1205"},{"name":"妈湾码头","code":"1204"},{"name":"沙井码头","code":"120b"},{"name":"蛇口","code":"1210"},{"name":"蛇口码头","code":"1201"},{"name":"深圳","code":"120f"},{"name":"盐田码头","code":"1200"},{"name":"渔人码头","code":"120d"},{"name":"招商港","code":"120e"},{"name":"孖仔岛","code":"1212"}]},{"name":"珠海","code":"13","sub":[{"name":"斗门港","code":"1305"},{"name":"港鑫港","code":"1308"},{"name":"高栏","code":"1314"},{"name":"高栏港","code":"1303"},{"name":"桂山港","code":"130e"},{"name":"豪通港","code":"1309"},{"name":"横琴客运港","code":"1304"},{"name":"洪湾港","code":"1311"},{"name":"加华国际货柜码头","code":"1310"},{"name":"井岸港","code":"130c"},{"name":"九洲港","code":"1300"},{"name":"九洲港码头","code":"130f"},{"name":"前山港","code":"130d"},{"name":"唐家港","code":"130b"},{"name":"湾仔港","code":"1306"},{"name":"武桥码头","code":"1312"},{"name":"香洲港","code":"1302"},{"name":"小塘","code":"1315"},{"name":"新业深水港","code":"130a"},{"name":"西域港","code":"1307"},{"name":"珠海","code":"1313"},{"name":"珠海港","code":"1301"}]},{"name":"佛山","code":"14","sub":[{"name":"白坭南拓货运港","code":"1416"},{"name":"北滘港","code":"1402"},{"name":"大码头","code":"142a"},{"name":"峰江码头","code":"141f"},{"name":"佛航通达港","code":"141c"},{"name":"佛山","code":"1429"},{"name":"佛山新港","code":"1400"},{"name":"佛山珠江货运码头","code":"1417"},{"name":"高棉港","code":"1420"},{"name":"高明","code":"143c"},{"name":"高明港","code":"140f"},{"name":"海燃港","code":"141e"},{"name":"海盛港","code":"141a"},{"name":"贺丰港","code":"1421"},{"name":"和乐","code":"1425"},{"name":"和乐港","code":"140b"},{"name":"九江","code":"1433"},{"name":"九江定安货运码头","code":"1404"},{"name":"九江欧浦码头","code":"1426"},{"name":"九江中外运码头","code":"1434"},{"name":"澜石","code":"142c"},{"name":"澜石码头","code":"1406"},{"name":"乐从","code":"143d"},{"name":"乐从炎明码头","code":"1405"},{"name":"勒流港","code":"1403"},{"name":"乐平","code":"1423"},{"name":"伦教","code":"1430"},{"name":"伦教码头","code":"1431"},{"name":"明珠货运码头","code":"1418"},{"name":"南港","code":"1436"},{"name":"南港码头","code":"1410"},{"name":"南海北村港","code":"1412"},{"name":"南海北村珠江港","code":"1419"},{"name":"南海港","code":"1415"},{"name":"南海海涛北港","code":"1413"},{"name":"南鲲","code":"143a"},{"name":"南鲲码头","code":"140c"},{"name":"南利","code":"1438"},{"name":"南利码头","code":"1424"},{"name":"南拓","code":"143b"},{"name":"南庄","code":"1439"},{"name":"南庄码头","code":"1414"},{"name":"容奇港","code":"1401"},{"name":"三山港","code":"140a"},{"name":"三水","code":"1432"},{"name":"三水港","code":"1407"},{"name":"三水南港码头","code":"1427"},{"name":"三水外贸港","code":"1409"},{"name":"三水西南港","code":"1408"},{"name":"沙头","code":"1437"},{"name":"盛发","code":"1428"},{"name":"石龙","code":"142b"},{"name":"石湾港","code":"141b"},{"name":"顺德发电厂港","code":"1422"},{"name":"顺德港","code":"1411"},{"name":"顺乐","code":"142d"},{"name":"顺乐港","code":"140d"},{"name":"陶港","code":"141d"},{"name":"小塘","code":"142f"},{"name":"小塘西货场","code":"142e"},{"name":"渔人港","code":"140e"},{"name":"战备码头","code":"1435"}]},{"name":"东莞","code":"15","sub":[{"name":"东莞国际码头","code":"1506"},{"name":"东沙港","code":"1509"},{"name":"海昌码头","code":"1501"},{"name":"海腾码头","code":"1505"},{"name":"宏兴码头","code":"150d"},{"name":"虎门","code":"1512"},{"name":"虎门港","code":"1500"},{"name":"虎门港澳港","code":"1507"},{"name":"虎门麻涌马士基码头","code":"1508"},{"name":"建晖","code":"1510"},{"name":"建晖纸厂","code":"150f"},{"name":"理文","code":"1514"},{"name":"理文码头","code":"1515"},{"name":"龙通","code":"1511"},{"name":"龙通码头","code":"1502"},{"name":"龙溪","code":"150e"},{"name":"荣轩货柜码头","code":"150b"},{"name":"沙田国际码头","code":"150a"},{"name":"沙田货运码头","code":"1504"},{"name":"石龙","code":"150c"},{"name":"太平港","code":"1503"},{"name":"新沙","code":"1516"},{"name":"中堂袁家涌码头","code":"1513"}]},{"name":"中山","code":"16","sub":[{"name":"ceshi","code":"160f"},{"name":"东升港","code":"1603"},{"name":"阜港码头","code":"160d"},{"name":"港湾","code":"1610"},{"name":"国际货柜码头","code":"1600"},{"name":"海事港","code":"1609"},{"name":"华星港","code":"160b"},{"name":"马安码头","code":"1607"},{"name":"内贸港","code":"1601"},{"name":"神湾","code":"1612"},{"name":"神湾港","code":"1606"},{"name":"水出港","code":"160c"},{"name":"腾步港","code":"1608"},{"name":"天字港","code":"1604"},{"name":"外贸码头","code":"1605"},{"name":"小榄","code":"1614"},{"name":"小榄港","code":"1602"},{"name":"渔政港","code":"160a"},{"name":"中山","code":"1613"},{"name":"中山港","code":"160e"},{"name":"中山外运","code":"1611"}]},{"name":"江门","code":"17","sub":[{"name":"恩平港","code":"1703"},{"name":"高宝隆","code":"1714"},{"name":"高沙港","code":"1702"},{"name":"公益","code":"171a"},{"name":"公益港","code":"170c"},{"name":"公益码头","code":"170d"},{"name":"国际货柜港","code":"1701"},{"name":"鹤山港","code":"1705"},{"name":"开平港","code":"1704"},{"name":"开平水口码头","code":"170a"},{"name":"开平泰宝","code":"1717"},{"name":"李锦记","code":"1711"},{"name":"三埠","code":"170f"},{"name":"三埠港","code":"1707"},{"name":"三埠港码头","code":"1712"},{"name":"双水小岗","code":"1715"},{"name":"水口码头","code":"1710"},{"name":"台山港","code":"1706"},{"name":"台山公益","code":"1716"},{"name":"外海","code":"1718"},{"name":"外贸港","code":"1700"},{"name":"西河口","code":"170e"},{"name":"新会","code":"1719"},{"name":"新会港","code":"1708"},{"name":"新会新港","code":"1713"},{"name":"新会新港/高宝隆","code":"1709"},{"name":"新会亚太码头","code":"170b"},{"name":"亚太","code":"171b"}]},{"name":"肇庆","code":"18","sub":[{"name":"瓷辉装卸港","code":"1811"},{"name":"大顶港","code":"1812"},{"name":"德庆","code":"1827"},{"name":"东威码头","code":"181d"},{"name":"富源装卸港","code":"1815"},{"name":"高要港","code":"1801"},{"name":"高要金马港","code":"180c"},{"name":"广隆码头","code":"180a"},{"name":"海全港","code":"180f"},{"name":"恒裕港","code":"1816"},{"name":"红蜘蛛西江浴码头","code":"181e"},{"name":"金辉港","code":"180b"},{"name":"金马浮吊港","code":"180d"},{"name":"金马浮吊装卸港","code":"1819"},{"name":"康州港","code":"1806"},{"name":"孔湾装卸港","code":"181c"},{"name":"联成装卸港","code":"1814"},{"name":"丽晶渡口","code":"1805"},{"name":"禄步装卸港","code":"181b"},{"name":"罗洪石场港","code":"180e"},{"name":"南江","code":"1821"},{"name":"南江口装卸码头","code":"1804"},{"name":"南江码头","code":"181f"},{"name":"三榕","code":"1825"},{"name":"三榕港","code":"1802"},{"name":"三榕沙场港","code":"1818"},{"name":"石井港","code":"1808"},{"name":"四会","code":"1820"},{"name":"四会永泰","code":"1823"},{"name":"天宇石场港","code":"181a"},{"name":"文峰码头","code":"1809"},{"name":"新侨港","code":"1817"},{"name":"永泰","code":"1822"},{"name":"悦城装卸码头","code":"1807"},{"name":"云浮","code":"1826"},{"name":"长亨港","code":"1810"},{"name":"长利港","code":"1813"},{"name":"肇庆","code":"1824"},{"name":"肇庆新港","code":"1800"},{"name":"肇庆永泰港","code":"1803"}]},{"name":"云浮","code":"19","sub":[{"name":"光明港","code":"1903"},{"name":"六都港","code":"1901"},{"name":"南江港","code":"1902"},{"name":"五一码头","code":"1904"},{"name":"云浮新港","code":"1900"},{"name":"中材金旺码头","code":"1905"}]},{"name":"惠州","code":"20","sub":[{"name":"博罗","code":"2009"},{"name":"国际港","code":"2001"},{"name":"宏兴港","code":"2002"},{"name":"宏兴码头","code":"2008"},{"name":"惠州","code":"2007"},{"name":"惠州港","code":"2000"},{"name":"龙溪","code":"2006"},{"name":"信和港","code":"2004"},{"name":"永鹏港","code":"2003"},{"name":"友联港","code":"2005"}]},{"name":"汕头","code":"21","sub":[{"name":"潮阳港","code":"2103"},{"name":"粮食港","code":"2104"},{"name":"南澳岛港","code":"2106"},{"name":"前江港","code":"2105"},{"name":"汕头港","code":"2100"},{"name":"汕头特区港","code":"2101"},{"name":"四港","code":"2107"},{"name":"西港","code":"2102"},{"name":"永泰港","code":"2108"}]},{"name":"清远","code":"22","sub":[{"name":"白庙港","code":"2201"},{"name":"高隆宝","code":"2206"},{"name":"鸿运港","code":"2205"},{"name":"江口港","code":"2203"},{"name":"清新港旺角港","code":"2204"},{"name":"五一港","code":"2200"},{"name":"渔政港","code":"2202"}]},{"name":"湛江","code":"23","sub":[{"name":"博茂港","code":"2307"},{"name":"海安港","code":"2300"},{"name":"海安新港","code":"2303"},{"name":"海滨港","code":"2304"},{"name":"流沙港","code":"2306"},{"name":"特呈港","code":"2305"},{"name":"渔人港","code":"2302"},{"name":"湛江东南港","code":"2301"}]},{"name":"阳江","code":"27","sub":[]},{"name":"福建","code":"24","sub":[{"name":"福州港","code":"2403"},{"name":"宁德港","code":"2400"},{"name":"莆田港","code":"2401"},{"name":"泉州港","code":"2402"},{"name":"厦门港","code":"2404"},{"name":"漳州港","code":"2405"}]},{"name":"广西","code":"25","sub":[{"name":"百色综合港","code":"2507"},{"name":"北海港","code":"2501"},{"name":"赤水","code":"251e"},{"name":"大冲口码头","code":"250e"},{"name":"防城港","code":"2500"},{"name":"贵港","code":"2505"},{"name":"贵港芭田码头","code":"2515"},{"name":"贵港大秦码头","code":"2513"},{"name":"贵港市南山码头","code":"2514"},{"name":"桂平","code":"2520"},{"name":"桂平北港","code":"2504"},{"name":"桂平新龙码头","code":"2512"},{"name":"贺州港昭平作业区","code":"2509"},{"name":"来宾港","code":"250a"},{"name":"李家莊","code":"251c"},{"name":"柳州鹧鸪江码头","code":"2508"},{"name":"龙联码头","code":"2510"},{"name":"南宁陈东港","code":"2518"},{"name":"南宁港","code":"2506"},{"name":"南宁海泰良庆码头","code":"2517"},{"name":"南宁三津港","code":"2519"},{"name":"钦州港","code":"2502"},{"name":"钦州正新码头","code":"2516"},{"name":"藤县蒙江河口木司码头","code":"251b"},{"name":"武林","code":"251f"},{"name":"武林港","code":"250f"},{"name":"武宣","code":"2521"},{"name":"武宣双狮码头","code":"2511"},{"name":"梧州","code":"251d"},{"name":"梧州河西老码头","code":"250c"},{"name":"梧州李家庄码头","code":"250b"},{"name":"梧州新港","code":"2503"},{"name":"梧州紫金村","code":"251a"},{"name":"越新赤水码头","code":"250d"}]},{"name":"海南","code":"26","sub":[{"name":"八所港","code":"2601"},{"name":"海口港","code":"2600"},{"name":"马村港","code":"2605"},{"name":"清澜港","code":"2604"},{"name":"三亚港","code":"2603"},{"name":"洋浦港","code":"2602"}]},{"name":"上海","code":"31","sub":[{"name":"宝山港","code":"3102"},{"name":"崇明港","code":"3109"},{"name":"川沙港","code":"3108"},{"name":"奉贤港","code":"3106"},{"name":"嘉定港","code":"3101"},{"name":"金山港","code":"3104"},{"name":"南汇港","code":"3107"},{"name":"青浦港","code":"3105"},{"name":"上海市区内河港","code":"310a"},{"name":"上海县港","code":"3100"},{"name":"松江港","code":"3103"}]},{"name":"江苏","code":"32","sub":[{"name":"滨海港","code":"320e"},{"name":"常熟港","code":"3209"},{"name":"大中港","code":"3202"},{"name":"东台港","code":"320c"},{"name":"高港","code":"3207"},{"name":"海安港","code":"320d"},{"name":"湖州港","code":"3218"},{"name":"江阴港","code":"3206"},{"name":"建湖港","code":"3203"},{"name":"靖江港","code":"321c"},{"name":"昆山港","code":"3204"},{"name":"李家巷港","code":"321a"},{"name":"南京地方港","code":"320a"},{"name":"南京港","code":"3220"},{"name":"南通市港","code":"3200"},{"name":"平湖港","code":"3219"},{"name":"冉里山港","code":"3210"},{"name":"泗安港","code":"3217"},{"name":"太仓港","code":"321b"},{"name":"泰兴港","code":"320b"},{"name":"魏塘港","code":"3214"},{"name":"梧桐港","code":"320f"},{"name":"小浦港","code":"3215"},{"name":"萧山港","code":"3212"},{"name":"硖石港","code":"3211"},{"name":"兴化港","code":"3205"},{"name":"盐城港","code":"3208"},{"name":"扬州港","code":"321e"},{"name":"仪征港","code":"321f"},{"name":"张家港市港","code":"3201"},{"name":"镇江港","code":"321d"},{"name":"雉城港","code":"3213"},{"name":"周浦港","code":"3216"}]},{"name":"安徽","code":"33","sub":[{"name":"安庆港","code":"330a"},{"name":"安庆港","code":"3303"},{"name":"蚌埠港","code":"330b"},{"name":"蚌埠港","code":"3309"},{"name":"池州港","code":"3305"},{"name":"荻港","code":"3304"},{"name":"合肥港","code":"3307"},{"name":"淮南港","code":"3308"},{"name":"马鞍山港","code":"3300"},{"name":"散兵港","code":"3306"},{"name":"铜陵港","code":"3302"},{"name":"芜湖港","code":"3301"}]},{"name":"江西","code":"34","sub":[{"name":"赣州港","code":"3402"},{"name":"九江港","code":"3400"},{"name":"南昌港","code":"3401"}]},{"name":"湖北","code":"35","sub":[{"name":"巴河港","code":"3504"},{"name":"鄂州港","code":"3506"},{"name":"黄冈港","code":"3507"},{"name":"黄石港","code":"3503"},{"name":"荆州港","code":"3502"},{"name":"兰溪港","code":"3505"},{"name":"利河口港","code":"350d"},{"name":"沙市港","code":"3509"},{"name":"田镇港","code":"3501"},{"name":"武汉港","code":"3508"},{"name":"武穴港","code":"3500"},{"name":"襄樊港","code":"350c"},{"name":"宜昌港","code":"350b"},{"name":"枝城港","code":"350a"}]},{"name":"湖南","code":"36","sub":[{"name":"常德港","code":"3602"},{"name":"城陵矶港","code":"3600"},{"name":"衡阳港","code":"3608"},{"name":"洪湖港","code":"3609"},{"name":"津市港","code":"3604"},{"name":"茅草街港","code":"3607"},{"name":"石首港","code":"360b"},{"name":"湘潭港","code":"3606"},{"name":"沅江港","code":"3603"},{"name":"岳阳港","code":"3601"},{"name":"长沙港","code":"360a"},{"name":"株州港","code":"3605"}]},{"name":"四川","code":"37","sub":[{"name":"巴东港","code":"3709"},{"name":"北碚港","code":"3703"},{"name":"丰都港","code":"370e"},{"name":"奉节港","code":"3704"},{"name":"涪陵港","code":"3701"},{"name":"合川港","code":"3706"},{"name":"合江港","code":"3711"},{"name":"江津港","code":"3710"},{"name":"乐山港","code":"3713"},{"name":"泸州港","code":"3705"},{"name":"水富港","code":"3712"},{"name":"万县港","code":"3700"},{"name":"万州港","code":"370c"},{"name":"巫山港","code":"370a"},{"name":"宜宾港","code":"3707"},{"name":"云阳港","code":"370b"},{"name":"长寿港","code":"370f"},{"name":"重庆港","code":"3702"},{"name":"忠县港","code":"370d"},{"name":"秭归港","code":"3708"}]},{"name":"京杭运河","code":"41","sub":[{"name":"宝应港","code":"4109"},{"name":"常州港","code":"4116"},{"name":"丹阳港","code":"410c"},{"name":"高邮港","code":"410a"},{"name":"杭州港","code":"4118"},{"name":"淮安港","code":"4107"},{"name":"淮阴港","code":"4108"},{"name":"湖州港","code":"4113"},{"name":"江都港","code":"410b"},{"name":"嘉兴港","code":"4114"},{"name":"济宁港","code":"4100"},{"name":"昆山港","code":"4110"},{"name":"邳州港","code":"4104"},{"name":"泗阳港","code":"4106"},{"name":"宿迁港","code":"4105"},{"name":"苏州港","code":"410f"},{"name":"铜山港","code":"4111"},{"name":"桐乡港","code":"4115"},{"name":"微山湖港","code":"4102"},{"name":"吴江港","code":"4112"},{"name":"武进港","code":"410d"},{"name":"无锡港","code":"410e"},{"name":"徐州港","code":"4103"},{"name":"宜兴港","code":"4117"},{"name":"枣庄港","code":"4101"}]},{"name":"淮河","code":"42","sub":[{"name":"蚌埠港","code":"4201"},{"name":"定远港","code":"4204"},{"name":"凤阳港","code":"4202"},{"name":"明光港","code":"4203"},{"name":"五河港","code":"4200"},{"name":"颍上港","code":"4205"}]},{"name":"辽宁","code":"61","sub":[{"name":"大连","code":"6100"},{"name":"丹东","code":"6101"},{"name":"葫芦岛港","code":"6105"},{"name":"锦州港","code":"6102"},{"name":"盘锦","code":"6104"},{"name":"营口港","code":"6103"}]},{"name":"津冀","code":"62","sub":[{"name":"黄骅","code":"6203"},{"name":"秦皇岛港","code":"6201"},{"name":"唐山","code":"6202"},{"name":"天津港","code":"6200"}]},{"name":"山东","code":"63","sub":[{"name":"滨州港","code":"6306"},{"name":"东营港","code":"6301"},{"name":"岚山港","code":"630a"},{"name":"连云港","code":"630b"},{"name":"龙口港","code":"6307"},{"name":"青岛港","code":"6300"},{"name":"日照港","code":"6305"},{"name":"石岛港","code":"6309"},{"name":"潍坊港","code":"6303"},{"name":"威海港","code":"6304"},{"name":"烟台港","code":"6302"},{"name":"张家埠港","code":"6308"}]},{"name":"苏浙沪","code":"64","sub":[{"name":"海门","code":"6409"},{"name":"嘉兴","code":"6408"},{"name":"南通","code":"6407"},{"name":"宁波港","code":"6401"},{"name":"上海港","code":"6400"},{"name":"台州港","code":"6402"},{"name":"温州港","code":"6405"},{"name":"盐城港","code":"6403"},{"name":"张家港","code":"6404"},{"name":"舟山港","code":"6406"}]},{"name":"福建","code":"65","sub":[{"name":"东山港","code":"6508"},{"name":"福州港","code":"6500"},{"name":"湄州湾港","code":"6507"},{"name":"宁德港","code":"6505"},{"name":"莆田港","code":"6502"},{"name":"泉州港","code":"6503"},{"name":"赛岐港","code":"6506"},{"name":"厦门港","code":"6501"},{"name":"漳州港","code":"6504"}]},{"name":"粤桂","code":"66","sub":[{"name":"北海港","code":"6610"},{"name":"潮州港","code":"6604"},{"name":"防城港","code":"6612"},{"name":"广州港","code":"6603"},{"name":"惠州港","code":"6608"},{"name":"虎门港","code":"660e"},{"name":"江门港","code":"660c"},{"name":"揭阳港","code":"6605"},{"name":"茂名港","code":"660d"},{"name":"钦州港","code":"6611"},{"name":"汕头港","code":"6606"},{"name":"汕尾港","code":"6607"},{"name":"深圳港","code":"6602"},{"name":"水东港","code":"6601"},{"name":"西堤港","code":"6609"},{"name":"阳江港","code":"6600"},{"name":"湛江港","code":"660f"},{"name":"中山港","code":"660a"},{"name":"珠海港","code":"660b"}]},{"name":"香港","code":"68","sub":[{"name":"昂船洲码头","code":"6805"},{"name":"国际货柜码头","code":"6806"},{"name":"葵涌码头","code":"6803"},{"name":"青衣码头","code":"6807"},{"name":"屯门","code":"6801"},{"name":"屯门码头","code":"6804"},{"name":"屯门内河码头","code":"6802"},{"name":"香港","code":"6800"}]},{"name":"澳门","code":"69","sub":[{"name":"澳门港","code":"6900"}]},{"name":"亚洲","code":"71","sub":[{"name":"巴基斯坦卡拉奇港","code":"7107"},{"name":"俄罗斯符拉迪沃斯托克港","code":"7105"},{"name":"日本横滨港","code":"7100"},{"name":"日本名古屋港","code":"7103"},{"name":"日本千叶港","code":"7102"},{"name":"日本神户港 ","code":"7101"},{"name":"斯里兰卡科伦坡港","code":"7109"},{"name":"土耳其伊斯坦布尔港","code":"710a"},{"name":"新加坡港","code":"7104"},{"name":"也门亚丁港","code":"7108"},{"name":"印度孟买港","code":"7106"}]},{"name":"美洲","code":"72","sub":[{"name":"阿根廷布宜诺斯艾利斯港","code":"7206"},{"name":"巴西里约热内卢港","code":"7205"},{"name":"加拿大温哥华港","code":"7202"},{"name":"美国旧金山港","code":"7201"},{"name":"美国洛杉矶港","code":"7200"},{"name":"美国纽约港","code":"7203"},{"name":"美国休斯敦港","code":"7204"}]},{"name":"欧洲","code":"73","sub":[{"name":"比利时安特卫普港","code":"7304"},{"name":"德国汉堡港","code":"7306"},{"name":"俄罗斯圣彼得堡港","code":"730a"},{"name":"法国勒阿弗尔港","code":"7305"},{"name":"法国马赛港","code":"7300"},{"name":"荷兰阿姆斯特丹港","code":"7309"},{"name":"荷兰鹿特丹港","code":"7303"},{"name":"罗马尼亚康斯坦萨港","code":"7302"},{"name":"葡萄牙里斯本港","code":"730c"},{"name":"瑞典哥德堡港","code":"730b"},{"name":"意大利热那亚港","code":"7301"},{"name":"英国利物浦港","code":"7308"},{"name":"英国伦敦港","code":"7307"}]},{"name":"非洲","code":"74","sub":[{"name":"埃及塞得港","code":"7402"},{"name":"埃及亚历山大港","code":"7401"},{"name":"利比里亚蒙罗维亚港","code":"7404"},{"name":"莫桑比克马普托港","code":"7400"},{"name":"南非开普敦港","code":"7403"},{"name":"尼瓜拉瓜","code":"7405"}]},{"name":"大洋洲","code":"75","sub":[{"name":"澳大利亚悉尼港","code":"7500"},{"name":"outside","code":"7502"},{"name":"新西兰奥克兰港","code":"7501"}]}];
}(Zepto);
// jshint ignore: end

/* jshint unused:false*/

+ function($) {
    "use strict";
    var sets=new Object();
    var format = function(data) {
        var result = [];
        for(var i=0;i<data.length;i++) {
            var d = data[i];
            if(d.name === "请选择港口") continue;
            result.push(d.name);
            sets[d.name] = d.code;
        }
        if(result.length) return result;
        return [""];
    };

    var sub = function(data) {
        if(!data.sub) return [""];
        return format(data.sub);
    };

    var getCities = function(d) {
        for(var i=0;i< raw.length;i++) {
            if(raw[i].name === d) return sub(raw[i]);
        }
        return [""];
    };

    var getDistricts = function(p, c) {
        for(var i=0;i< raw.length;i++) {
            if(raw[i].name === p) {
                for(var j=0;j< raw[i].sub.length;j++) {
                    if(raw[i].sub[j].name === c) {
                        return sub(raw[i].sub[j]);
                    }
                }
            }
        }
        return [""];
    };

    var raw = $.smConfig.rawCitiesData;
    var provinces = raw.map(function(d) {
        return d.name;
    });
    var initCities = sub(raw[0]);
    var initDistricts = [""];

    var currentProvince = provinces[0];
    var currentCity = initCities[0];
    var currentDistrict = initDistricts[0];

    var t;
    var defaults = {

        cssClass: "city-picker",
        rotateEffect: false,  //为了性能

        onChange: function (picker, values, displayValues) {
            var newProvince = picker.cols[0].value;
            var newCity;
            if(newProvince !== currentProvince) {
                // 如果Province变化，节流以提高reRender性能
                clearTimeout(t);

                t = setTimeout(function(){
                    var newCities = getCities(newProvince);
                    newCity = newCities[0];
                    var newDistricts = getDistricts(newProvince, newCity);
                    picker.cols[1].replaceValues(newCities);
                    //picker.cols[2].replaceValues(newDistricts);
                    currentProvince = newProvince;
                    currentCity = newCity;
                    picker.updateValue();
                }, 200);
                return;
            }
            newCity = picker.cols[1].value;
            if(newCity !== currentCity) {
                //picker.cols[2].replaceValues(getDistricts(newProvince, newCity));
                currentCity = newCity;
                picker.updateValue();
            }
            var nodeVal = $(picker.params.id).siblings("input[type='hidden']").get(0);
            $(nodeVal).val(sets[newCity]);
        },

        cols: [
        {
            textAlign: 'center',
            values: provinces,
            cssClass: "col-province"
        },
        {
            textAlign: 'center',
            values: initCities,
            cssClass: "col-city"
        }
        ]
    };

    $.fn.portPicker = function(params) {
        return this.each(function() {
            if(!this) return;
            var p = $.extend(defaults, params);
            //计算value
            if (p.value) {
                $(this).val(p.value.join(' '));
            } else {
                var val = $(this).val();
                val && (p.value = val.split(' '));
            }

            if (p.value) {
                //p.value = val.split(" ");
                if(p.value[0]) {
                    currentProvince = p.value[0];
                    p.cols[1].values = getCities(p.value[0]);
                }
                if(p.value[1]) {
                    currentCity = p.value[1];
                    //p.cols[2].values = getDistricts(p.value[0], p.value[1]);
                } else {
                    //p.cols[2].values = getDistricts(p.value[0], p.cols[1].values[0]);
                }
                //!p.value[2] && (p.value[2] = '');
                //currentDistrict = p.value[2];
            }
            $(this).picker(p);
        });
    };

}(Zepto);
