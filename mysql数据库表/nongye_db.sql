/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : nongye_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-07-19 16:09:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_crop`
-- ----------------------------
DROP TABLE IF EXISTS `t_crop`;
CREATE TABLE `t_crop` (
  `cropId` int(11) NOT NULL auto_increment COMMENT '农作物id',
  `cropClassObj` int(11) NOT NULL COMMENT '农作物分类',
  `cropName` varchar(30) NOT NULL COMMENT '农作物名称',
  `cropPhoto` varchar(60) NOT NULL COMMENT '农作物图片',
  `cropDesc` varchar(8000) NOT NULL COMMENT '农作物描述',
  `cropMemo` varchar(800) default NULL COMMENT '备注信息',
  `addTime` varchar(20) default NULL COMMENT '添加时间',
  PRIMARY KEY  (`cropId`),
  KEY `cropClassObj` (`cropClassObj`),
  CONSTRAINT `t_crop_ibfk_1` FOREIGN KEY (`cropClassObj`) REFERENCES `t_cropclass` (`cropClassId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_crop
-- ----------------------------
INSERT INTO `t_crop` VALUES ('1', '1', '玉米', 'upload/cc6a99db-6bbd-4c6a-8342-64ce5bd69a4a.jpg', '<p><span style=\"color: rgb(76, 76, 76); font-family: Arial, Helvetica, sans-serif, 鏂板畫浣�; font-size: 14px; background-color: rgb(255, 255, 255);\">玉米是分布最广泛的粮食作物之一，种植面积仅次于小麦和水稻。种植范围从北纬58°(加拿大和俄罗斯)至南纬40°(南美)。世界上整年每个月都有玉米成熟。玉米是美国最重要的粮食作物，产量约占世界产量的一半，其中约2/5供外销。中国年产玉米占世界第二位，以下是巴西、墨西哥、阿根廷。&nbsp;</span></p>', ' 我国玉米的播种面积很大，分布也很广，是我国北方和西南山区及其它旱谷地区人民的主要粮食之一。 山东省莱西市为玉米的重要产区之一，开鲁县的玉米质量非常高。', '2018-04-07 12:13:42');
INSERT INTO `t_crop` VALUES ('2', '4', '花生', 'upload/839473f7-193b-4674-ba4c-beddb95f7134.jpg', '<p><span style=\"color: rgb(76, 76, 76); font-family: Arial, Helvetica, sans-serif, 鏂板畫浣�; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"color: rgb(76, 76, 76); font-family: Arial, Helvetica, sans-serif, 鏂板畫浣�; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp; &nbsp;&nbsp;</span>花生为豆科作物，优质食用油主要油料品种之一，又名“落花生”或“长生果”。花生是一年生草本植物。起源于南美洲热带、亚热带地区。约于十六世纪传入我国，十九世纪末有所发展。现在全国各地均有种植，主要分布于辽宁、山东、河北、河南、江苏、福建、广东、广西、四川等省（区）。</span><br/><span style=\"color: rgb(76, 76, 76); font-family: Arial, Helvetica, sans-serif, 鏂板畫浣�; font-size: 14px; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp; 世界生产花生的国家有100多个,亚洲最为普遍，次为非洲。但作商品生产的仅10多个国家，主要生产国中以印度和中国栽培面积和生产量最大,前者约720万公顷，560万吨,后者为355.3万公顷，675.7万吨(1985)。其他国家有塞内加尔、尼日利亚和美国等。</span></p>', '全国各地均有种植，主要分布于辽宁、山东、河北、河南、江苏、福建、广东、广西、四川等省（区）。 ', '2018-04-07 12:26:36');

-- ----------------------------
-- Table structure for `t_cropclass`
-- ----------------------------
DROP TABLE IF EXISTS `t_cropclass`;
CREATE TABLE `t_cropclass` (
  `cropClassId` int(11) NOT NULL auto_increment COMMENT '农作物分类id',
  `cropClassName` varchar(20) NOT NULL COMMENT '农作物分类名称',
  PRIMARY KEY  (`cropClassId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cropclass
-- ----------------------------
INSERT INTO `t_cropclass` VALUES ('1', '粮食作物');
INSERT INTO `t_cropclass` VALUES ('2', '蔬菜类作物');
INSERT INTO `t_cropclass` VALUES ('3', '豆类作物');
INSERT INTO `t_cropclass` VALUES ('4', '油料作物');

-- ----------------------------
-- Table structure for `t_disease`
-- ----------------------------
DROP TABLE IF EXISTS `t_disease`;
CREATE TABLE `t_disease` (
  `diseaseId` int(11) NOT NULL auto_increment COMMENT '病害id',
  `cropObj` int(11) NOT NULL COMMENT '农作物',
  `diseaseName` varchar(20) NOT NULL COMMENT '病害名称',
  `diseasePhoto` varchar(60) NOT NULL COMMENT '病害图片',
  `diseaseDesc` varchar(8000) NOT NULL COMMENT '病害描述',
  `cureDesc` varchar(8000) NOT NULL COMMENT '防治方法',
  `addTime` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`diseaseId`),
  KEY `cropObj` (`cropObj`),
  CONSTRAINT `t_disease_ibfk_1` FOREIGN KEY (`cropObj`) REFERENCES `t_crop` (`cropId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_disease
-- ----------------------------
INSERT INTO `t_disease` VALUES ('1', '2', '花生缺氮症', 'upload/4094ea6a-d363-4f34-a106-9c533d4ef344.jpg', '<p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; white-space: normal; background-color: rgb(255, 255, 255);\">花生缺氮是由土壤中缺少氮元素造成的，同时，在砂土、砂壤土中最容易缺氮，而这种缺素症的防治方法仍是补充虽缺失的营养元素。<br/>花生缺氮症发病原因<br/>花生对氮肥不大敏感，但前作施入有机肥少，或土壤含氮量低，容易缺氮。砂土、砂壤土较壤土易缺氮。&nbsp;</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; white-space: normal; background-color: rgb(255, 255, 255);\">更多农资信息，请关注<strong style=\"margin: 0px; padding: 0px;\"><a href=\"http://www.zhongnong.com/\" target=\"_blank\" style=\"margin: 0px; padding: 0px; color: rgb(51, 51, 51); text-decoration: none;\">中国农资网</a></strong>。</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; white-space: normal; background-color: rgb(255, 255, 255);\">免责声明：本站部分信息摘自互联网，如有侵犯，请联系我们立刻删除。另，本文的真实性和及时性本站不做任何承诺，仅供读者参考</p><p><br/></p>', '<p><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">花生缺氮症防治方法</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">施足有机肥，始花前10天每亩施用硫酸铵5～10千克，最好与有机肥沤15～20天后施用。</span></p>', '2018-04-07 12:13:54');
INSERT INTO `t_disease` VALUES ('2', '1', '玉米锈病', 'upload/c4058525-be0d-482d-af54-df9621762c0c.jpg', '<p><strong style=\"margin: 0px; padding: 0px;\">简介：</strong></p><p>　　英文名Cornrust病原玉米柄锈菌PucciniasorghiSchw.，属担子菌亚门。危害玉米常见病害。通常在玉米生育的中后期发生，为害较轻。个别地方或年份发病重。分布华南、西南、华北、东北、华东以及西北地区均有发生。</p><p><strong style=\"margin: 0px; padding: 0px;\">　　为害症状：</strong></p><p>　　小点，以后突起，并扩展为圆形至长圆形，黄褐色或褐色，周围表皮翻起，散出铁锈色粉末，即病菌的夏孢子。后期病斑上生长圆形黑色突起，破裂后露出黑褐色粉末，即病菌的冬孢子。</p><p><strong style=\"margin: 0px; padding: 0px;\">　　病原物：</strong></p><p>　　(1)形态：①近球形或椭圆形，淡黄褐色，大小(24～332)微米×(21～30微米，表面具微刺，膜厚1.5～2微米，赤道附近具4个发芽孔。②冬孢子长椭圆形或椭圆形，栗褐色顶端圆，少数扁平，表面光滑，具1个隔膜，隔膜处稍溢缩，大小(2～46)微米×(14～25)微米。柄淡黄色至淡褐色，长达80微米。(2)特性：孢子萌发适温20～30℃，以25℃为最适。相对湿度高利于锈病的流行与蔓延。(3)特性：孢子萌发适温20～30℃，以25℃为最适。相对湿度高利于锈病的流行与蔓延。</p><p><strong style=\"margin: 0px; padding: 0px;\">　　侵染循环：</strong></p><p>　　病原以冬孢子在病株上越冬，冬季温暖地区夏孢子也可越冬。虽有报道该菌有转株寄主，其性孢子和锈孢子阶段发生于酢浆草上，但在田间自然条件下未找到，故对病害传播作用不大。田间病害传播靠夏孢子一代代重复侵染，从春玉米传到夏玉米，再传到秋玉米。多堆柄锈菌未发现性孢子和锈孢子阶段，以冬孢子、夏孢子和菌丝体在玉米植株上越冬，夏孢子重复侵染为害。</p><p><strong style=\"margin: 0px; padding: 0px;\">　　发生因素：</strong></p><p>　　(1)寄主抗性：不同玉米品种对锈病的抗性有明显差异，通常早熟品种易发病，马齿型品种较抗病。筛选抗源，加强玉米的抗锈育种，是预防玉米锈病的有效措施。美国和我国台湾等地通过种植抗病品种，已取得明显成效。我国的自交系齐319对玉米南方锈病表现免疫，由其选育的几个杂交种如鲁单891鲁单50等均对玉米锈病表现较好的抗性。(2)环境：温度和湿度条件是影响发病最重要的气候因素，玉米普通锈病以温暖高湿天气适于发病，在气温16～23℃，相对湿度100%时发病重，夏孢子在13～16℃时萌发最好。有报道指出夜间温度是影响夏孢子形成的主要因素，8℃和32℃夏孢子形成很少且延迟。空气湿度对发病影响也很大，多雾天气病重，据试验在20℃时接种夏孢子，再间歇喷雾12小时的发病率明显高于喷雾6小时，但孢子发芽率两处理无大差异，说明喷雾6小时已满足夏孢子发芽，但难以侵染。玉米南方锈病在高温高湿的环境加重发病，以27℃最适发病，夏孢子以24～28℃萌发最好，从孢子发芽侵入到产生新的夏孢子约经7～10天。在全年大多数时间有菌源存在的台湾省，春玉米发病与发病前7天的气温关系最密切，秋玉米发病与发病前8～14天的气温密切相关。(3)栽培：偏施氮肥发病重。</p><p><br/></p>', '<p><strong style=\"margin: 0px; padding: 0px;\">防治方法：</strong></p><p>　　农业防治：选用抗病品种。增施磷<strong style=\"margin: 0px; padding: 0px;\"><a target=\"_blank\" href=\"http://www.zhongnong.com/Products/d61.html\" style=\"margin: 0px; padding: 0px; color: rgb(51, 51, 51); text-decoration: none;\">钾肥</a></strong>，避免偏施、过施氮肥，提高寄主抗病力。加强田间管理，清除酢浆草和病残体，集中深埋或烧毁。</p><p><br/></p>', '2018-04-07 12:37:42');

-- ----------------------------
-- Table structure for `t_expert`
-- ----------------------------
DROP TABLE IF EXISTS `t_expert`;
CREATE TABLE `t_expert` (
  `expertUserName` varchar(30) NOT NULL COMMENT 'expertUserName',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `expertPhoto` varchar(60) NOT NULL COMMENT '专家照片',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `zhicheng` varchar(20) NOT NULL COMMENT '职称',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `expertDesc` varchar(8000) NOT NULL COMMENT '专家介绍',
  PRIMARY KEY  (`expertUserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_expert
-- ----------------------------
INSERT INTO `t_expert` VALUES ('zj001', '123', '董德显', '男', 'upload/4d2a785b-9e12-4256-9836-6bb2c9fa9505.jpg', '2018-04-04', '农业专家教授', '13985038093', '福建福州', '<p>男，1936年生，教授。从事专业：地理信息系统、国土资源。专业特长：土地信息系统、农业遥感。提供内容：国外现代农业发展动态，农业科技信息的发布，新技术、新产品介绍。</p>');
INSERT INTO `t_expert` VALUES ('zj002', '123', '李广兴', '男', 'upload/c570893a-6c2c-4868-9a8a-ac931ebb34c2.jpg', '2018-04-03', '农业专家副教授', '13980809243', '四川成都', '<p>男，副教授。从事专业：兽医病理学，传染病学与预防兽医学</p>');

-- ----------------------------
-- Table structure for `t_leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveword`;
CREATE TABLE `t_leaveword` (
  `leaveWordId` int(11) NOT NULL auto_increment COMMENT '留言id',
  `leaveTitle` varchar(80) NOT NULL COMMENT '留言标题',
  `leaveContent` varchar(2000) NOT NULL COMMENT '留言内容',
  `userObj` varchar(30) NOT NULL COMMENT '留言人',
  `leaveTime` varchar(20) default NULL COMMENT '留言时间',
  `replyContent` varchar(1000) default NULL COMMENT '专家回复',
  `replyTime` varchar(20) default NULL COMMENT '回复时间',
  PRIMARY KEY  (`leaveWordId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_leaveword_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveword
-- ----------------------------
INSERT INTO `t_leaveword` VALUES ('1', '我想种植玉米，要注意啥', '我家在四川达州农村，这里的气候可以种玉米吧？', 'user1', '2018-04-07 12:16:17', '没问题，注意选择肥沃的土壤！', '2018-04-07 12:16:21');
INSERT INTO `t_leaveword` VALUES ('2', '种植过程需要注意什么', '我准备种植玉米，其中我需要注意啥？', 'user1', '2018-04-15 11:49:24', '需要保持充足的营养，防止病虫害！', '2018-04-17 00:38:49');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `noticeId` int(11) NOT NULL auto_increment COMMENT '新闻id',
  `title` varchar(80) NOT NULL COMMENT '标题',
  `content` varchar(5000) NOT NULL COMMENT '新闻内容',
  `publishDate` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '农业专家网站成立了', '<p>朋友们有关于农业的问题，都可以来这里找寻答案，也可以向专家求助 ！</p>', '2018-04-07 12:16:48');

-- ----------------------------
-- Table structure for `t_pest`
-- ----------------------------
DROP TABLE IF EXISTS `t_pest`;
CREATE TABLE `t_pest` (
  `pestId` int(11) NOT NULL auto_increment COMMENT '虫害id',
  `cropObj` int(11) NOT NULL COMMENT '农作物',
  `pestName` varchar(40) NOT NULL COMMENT '虫害名称',
  `pestPhoto` varchar(60) NOT NULL COMMENT '虫害图片',
  `pestDesc` varchar(8000) NOT NULL COMMENT '虫害描述',
  `cureDesc` varchar(8000) NOT NULL COMMENT '防治方法',
  `addTime` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`pestId`),
  KEY `cropObj` (`cropObj`),
  CONSTRAINT `t_pest_ibfk_1` FOREIGN KEY (`cropObj`) REFERENCES `t_crop` (`cropId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_pest
-- ----------------------------
INSERT INTO `t_pest` VALUES ('1', '2', '花生斑驳病毒病', 'upload/d99db9b0-7b4b-4251-8fdb-533784adfe5e.jpg', '<p><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;花生病毒病是对花生危害最严重的病害，为害我国花生共有4种病毒，分别是花生条纹、黄花叶病、花生矮化和花生芽枯病毒。北方花生常见的病毒病主要是前三种，其中危害最严重的是花生普通花叶病毒病。除芽枯病主要由蓟马传播外，其他三种病害则通过种子和蚜虫传播。</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;一、</span><strong style=\"margin: 0px; padding: 0px; color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; white-space: normal; background-color: rgb(255, 255, 255);\">为害症状及侵染</strong><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;1.条纹病毒病：由</span><strong style=\"margin: 0px; padding: 0px; color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; white-space: normal; background-color: rgb(255, 255, 255);\"><a target=\"_blank\" href=\"http://www.zhongnong.com/Products/d303.html\" style=\"margin: 0px; padding: 0px; color: rgb(51, 51, 51); text-decoration: none;\">花生</a></strong><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">条纹病毒（PStV）引起。感病植株先在顶端嫩叶上出现褪绿斑，后发展成浅绿与绿色相间的斑驳，沿叶脉形成断续绿色条纹或橡叶状花纹，或一直呈系统性的斑驳症状。感病早的植株稍有矮化。</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;2.黄花叶病毒病：病原为黄瓜花叶病毒中国花生株系（CMV-CA），病株先在顶端嫩叶上出现褪绿黄斑，叶脉变淡，叶色发黄，叶缘上卷，随后发展为黄绿相间的黄花叶症状，病株中度矮化。该病常与花生条纹病毒病混合发生，表现黄斑驳、绿色条纹等复合症状。</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;3.普通花叶病毒病：病原为花生矮化病毒（PSV）,病株顶端叶片出现褪绿斑，并发展成绿色与浅绿相间的花叶，新长出的叶片通常展开时是黄色的，但可以转变成正常绿色。病叶变窄小，叶缘有时出现波状扭曲。病株结荚少而小，有时畸形或开裂。</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;二、</span><strong style=\"margin: 0px; padding: 0px; color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; white-space: normal; background-color: rgb(255, 255, 255);\">病害循环</strong><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;花生条纹病毒通过带毒花生种子越冬，病害一般在花生出苗10天后开始发生, 多为种传病苗，病毒被蚜虫在田间迅速扩散。通常病害在花期形成发生高峰；黄瓜花叶病毒通过带毒花生种子越冬，种传花生病苗出土后即表现症状,病毒被蚜虫在田间迅速扩散。在流行年份花期形成发病高峰。花生矮化病毒种传率很低。花生出苗后, 有翅蚜向花生地迁飞,同时将病毒从其他越冬寄主上传入。</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">可见三种病毒均可通过花生种子传播，带毒种子是条纹病毒病和黄花叶病毒病的主要初侵染源，普通花叶病毒病和黄花叶病毒病还可在田间越冬寄主上存活，成为来年病害的初侵染源。在花生生长季节，三种病毒均主要靠蚜虫以非持久性方式在田间传播。</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;三、</span><strong style=\"margin: 0px; padding: 0px; color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; white-space: normal; background-color: rgb(255, 255, 255);\">发病条件</strong><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;三种病毒病的发生和流行与毒源数量、介体蚜虫数量、花生品种和生育期有密切关系。在存在毒源和感病品种的条件下，蚜虫发生早晚和数量是影响病毒病流行的主要因素。传毒蚜虫发生早、数量多、传毒效率高，病害就易于流行。种子带毒率与种子大小成负相关，大粒种子带毒率低，小粒种子带毒率高。传播病毒的蚜虫主要是田间活动的有翅蚜。一般花生苗期降雨少、气候温和、干燥，易导致蚜虫发生早，数量大，易引起病害严重流行，反之则发病轻。</span></p>', '<p><strong style=\"margin: 0px; padding: 0px; color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; white-space: normal; background-color: rgb(255, 255, 255);\">防治方法</strong><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;1、是采用无毒或低毒种子，杜绝或减少初侵染源。</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;2、是选用感病轻和种传率低的品种，并且选择大粒子仁作种子。</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;3、推广地膜覆盖种植，地膜具有一定的驱蚜效果，可以减轻病毒病的为害。</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;4、早期拔除种传病苗。</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;5、及时清除田间和周围杂草，减少蚜虫来源，可减轻病害发生。</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;6、药剂治蚜，也可用25％的辛拌磷（812）盖种，每亩用药量0.5千克，花生出苗后，要及时检查，发理蚜虫及时用40%乐果乳油800倍液喷洒，以杜绝蚜虫传毒。</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp; &nbsp;7、搞好病害检疫，禁止从病区调种。</span></p>', '2018-04-07 12:14:04');
INSERT INTO `t_pest` VALUES ('2', '2', '花生田灰地种蝇', 'upload/f80371ac-828d-4987-9ae7-5fa954eaa01c.jpg', '<p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; white-space: normal; background-color: rgb(255, 255, 255);\">&nbsp;<strong style=\"margin: 0px; padding: 0px;\">学名</strong><br/>&nbsp;&nbsp;&nbsp;&nbsp;Deliaplatura(Meigen)双翅目，花蝇科。别名灰种蝇、种蝇、地蛆、种蛆、菜蛆、根蛆。异名Hylemyiacilicrura(Rondani)、H.cana(Macq.)、HylemyiaplaturaMeigen、ChortophilacilicruraRondani、AnthomyiazeaeRiley。分布在全国各地。<br/>&nbsp;&nbsp;&nbsp;&nbsp;<strong style=\"margin: 0px; padding: 0px;\">寄主</strong><br/>&nbsp;&nbsp;&nbsp;&nbsp;花生、豆类、瓜类、棉花、十字花科等。<br/>&nbsp;&nbsp;&nbsp;&nbsp;<strong style=\"margin: 0px; padding: 0px;\">为害特点</strong><br/>&nbsp;&nbsp;&nbsp;&nbsp;以幼虫钻入花生种子里，咬食子叶或胚芽，致种子不能发芽或腐烂；也可钻入幼苗茎内，将茎蛀食成空心而枯萎。<br/>&nbsp;&nbsp;&nbsp;&nbsp;<strong style=\"margin: 0px; padding: 0px;\">形态特征</strong><br/>&nbsp;&nbsp;&nbsp;&nbsp;成虫：体长4～6mm，雄蝇暗黄色至暗褐色，两复眼几乎相接触，触角黑色，胸部背面有3条黑色纵纹，后足胫节内下方生有一列稠密、大致等长、末端弯曲的短毛，腹部背面中央有一条黑色纵纹，各腹节问有一黑色横纹，使腹背形成明显的小方块。雌蝇灰色或灰黄色，两复眼间距较宽，约等于头宽1／3。中足胫节的外上方生有一根刚毛，腹部背面中央纵纹不明显。成虫前翅基背毛均短，其长度不及盾间沟后背中毛的1／2。卵：乳白色，长约1mm，长椭圆形，稍弯，弯内有纵沟陷，表面具网状纹。幼虫：老熟幼虫体长7～8mm，乳白色略带浅黄色。头退化，仅有1对黑色口钩。整个体形前端细后端粗，腹末端呈截面具7对突起，各突起均不分叉，第7对极小而且因着生在第6对中间的稍内侧，所以从上面有时看不到，第1对和第2对在等高位置，第5对与第6对等长。蛹：长4～5mm，长椭圆形，红褐色或黄褐色，尾端可见7对突起。</p><p style=\"margin-top: 0px; margin-bottom: 0px; padding: 0px; color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; white-space: normal; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;<strong style=\"margin: 0px; padding: 0px;\">生活习性</strong><br/>&nbsp;&nbsp;&nbsp;&nbsp;全国分布，由北而南年发生2～6代(黑龙江2～3代，辽宁3～4代，陕西4代，江西、湖南5～6代)，以蛹在土中越冬。翌春羽化的成虫在豆苗附近的土表上或黄瓜苗的根部产卵，孵化出的蛆即钻入作物的嫩茎内，使作物因水分不足而萎凋，以后逐渐腐烂枯死。本种以第一代为害严重，有些年份在秋天发生的第四代与萝卜地种蝇混杂发生为害白菜、萝卜。成虫以晴天中午前后最活跃，对未腐熟的粪肥及发酵的饼肥有很强的趋性。</p><p><br/></p>', '<p><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;</span><strong style=\"margin: 0px; padding: 0px; color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; white-space: normal; background-color: rgb(255, 255, 255);\">防治方法</strong><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;(1)花生田施用的基肥和饼肥必须充分腐熟，均匀深施后盖土，地面上不露粪肥，减少种蝇产卵。</span><br/><span style=\"color: rgb(51, 51, 51); font-family: 宋体, &quot;Arial Narrow&quot;, arial, serif; font-size: 14px; text-align: justify; background-color: rgb(255, 255, 255);\">&nbsp;&nbsp;&nbsp;&nbsp;(2)适时播种，减少低温造成的烂种。&nbsp;</span></p>', '2018-04-07 12:34:38');

-- ----------------------------
-- Table structure for `t_postinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_postinfo`;
CREATE TABLE `t_postinfo` (
  `postInfoId` int(11) NOT NULL auto_increment COMMENT '帖子id',
  `title` varchar(80) NOT NULL COMMENT '帖子标题',
  `content` varchar(5000) NOT NULL COMMENT '帖子内容',
  `hitNum` int(11) NOT NULL COMMENT '浏览量',
  `userObj` varchar(30) NOT NULL COMMENT '发帖人',
  `addTime` varchar(20) default NULL COMMENT '发帖时间',
  PRIMARY KEY  (`postInfoId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_postinfo_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_postinfo
-- ----------------------------
INSERT INTO `t_postinfo` VALUES ('1', '我家种的花生生病了', '<p>我家的花生叶子上有黄色的斑点，不知道是什么东西？</p>', '16', 'user1', '2018-04-07 12:15:35');
INSERT INTO `t_postinfo` VALUES ('2', 'aa', '<p>bb<br/></p>', '16', 'user1', '2018-04-15 11:33:51');

-- ----------------------------
-- Table structure for `t_reply`
-- ----------------------------
DROP TABLE IF EXISTS `t_reply`;
CREATE TABLE `t_reply` (
  `replyId` int(11) NOT NULL auto_increment COMMENT '回复id',
  `postInfoObj` int(11) NOT NULL COMMENT '被回帖子',
  `content` varchar(2000) NOT NULL COMMENT '回复内容',
  `userObj` varchar(30) NOT NULL COMMENT '回复人',
  `replyTime` varchar(20) default NULL COMMENT '回复时间',
  PRIMARY KEY  (`replyId`),
  KEY `postInfoObj` (`postInfoObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_reply_ibfk_1` FOREIGN KEY (`postInfoObj`) REFERENCES `t_postinfo` (`postInfoId`),
  CONSTRAINT `t_reply_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_reply
-- ----------------------------
INSERT INTO `t_reply` VALUES ('1', '1', '谁可以来我家看看', 'user1', '2018-04-07 12:15:42');
INSERT INTO `t_reply` VALUES ('2', '2', '求助啊！', 'user1', '2018-04-15 11:53:20');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('user1', '123', '张晓芬', '女', '2018-04-07', 'upload/8f8244b6-620b-45b7-975c-15b7c1dba20c.jpg', '13573598343', 'xiaofen@163.com', '四川成都红星路13号', '2018-04-07 12:14:57');
INSERT INTO `t_userinfo` VALUES ('user2', '123', '王晓容', '女', '2018-04-10', 'upload/NoImage.jpg', '13598308394', 'xiaorong@163.com', '四川成都海洋咯13号', '2018-04-15 12:01:44');

-- ----------------------------
-- Table structure for `t_wxqk`
-- ----------------------------
DROP TABLE IF EXISTS `t_wxqk`;
CREATE TABLE `t_wxqk` (
  `qkId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `wxqkType` varchar(20) NOT NULL COMMENT '文献期刊类别',
  `xueke` varchar(20) NOT NULL COMMENT '学科',
  `title` varchar(40) NOT NULL COMMENT '篇名',
  `wxqkPhoto` varchar(60) NOT NULL COMMENT '文献期刊图片',
  `author` varchar(20) NOT NULL COMMENT '作者',
  `km` varchar(20) NOT NULL COMMENT '刊名',
  `keywordInfo` varchar(50) NOT NULL COMMENT '关键词',
  `zhaiyao` varchar(800) NOT NULL COMMENT '摘要',
  `daoshi` varchar(20) NOT NULL COMMENT '导师',
  `publishDate` varchar(20) default NULL COMMENT '发布日期',
  `wxqkFile` varchar(60) NOT NULL COMMENT '文献期刊文件',
  PRIMARY KEY  (`qkId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_wxqk
-- ----------------------------
INSERT INTO `t_wxqk` VALUES ('1', '农业文献', '农业学科', '植物病理学研究进展', 'upload/bcc2d7a8-4f3f-44a5-aaf4-c8bfa9b19110.jpg', '王权', '病虫害防治周刊', '花生，病虫害', '讲解了常见的植物病虫害', '王雪萍', '2018-04-07', 'upload/acecfec4-e421-449c-b7a4-2dd4331b3533.doc');

-- ----------------------------
-- Table structure for `t_zhenduan`
-- ----------------------------
DROP TABLE IF EXISTS `t_zhenduan`;
CREATE TABLE `t_zhenduan` (
  `zhenduanId` int(11) NOT NULL auto_increment COMMENT '诊断id',
  `zhengzhuang` varchar(40) NOT NULL COMMENT '症状标题',
  `zhenzhuangPhoto` varchar(60) NOT NULL COMMENT '症状图片',
  `bingyin` varchar(800) NOT NULL COMMENT '病因',
  `tezheng` varchar(800) NOT NULL COMMENT '特征',
  `fbtj` varchar(800) NOT NULL COMMENT '发病条件',
  `nyyf` varchar(800) NOT NULL COMMENT '农业预防',
  `zlff` varchar(800) NOT NULL COMMENT '治疗方法',
  `addTime` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`zhenduanId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_zhenduan
-- ----------------------------
INSERT INTO `t_zhenduan` VALUES ('1', '症状标题111111', 'upload/10a5084e-0721-445d-899b-47b7db850eef.jpg', '<p>病因11111</p>', '<p>特征111111</p>', '<p>发病条件111</p>', '<p>预防2222</p>', '<p>治疗方法122242</p>', '2018-04-07 12:14:23');
INSERT INTO `t_zhenduan` VALUES ('2', ' 玉米穗腐病', 'upload/5ff5089f-6ba0-4e6e-9f65-50902e232c5b.jpg', '<p>　　有30多种，主要有大、小斑病，丝黑穗病，青枯病，病毒病和茎腐病等。通过选用抗病品种和加强管理预防之。虫害有玉米螟、地老虎、蝼蛄、红蜘蛛、高粱条螟和粘虫等。采用药剂防治。</p>', '<p>　　玉米穗腐病在田间自幼苗至成熟期都可发生，最典型的症状为种子霉烂、弱苗、茎腐、穗腐，其中以穗腐的经济损失最为严重。</p><p>　　种子霉烂与弱苗：病菌污染粘附在种子表面，经播种后，受害重者不能发芽而霉烂，造成缺苗断垄；轻者出苗后生长细弱缓慢，形成弱苗。</p><p><br/></p>', '<p>玉米穗腐病在庄浪县的发生面积随感病品种种植面积和年份而变化，2003年发病面积3500hm2,病穗率46%；2004年发病面积3000 hm2，病穗率26.5%，2005年发病面积4500 hm2，病穗率44.3%，2006年发病面积5300 hm2 ，病穗率23..9%。</p>', '<p>　心叶期和穗期：以防治玉米螟、粘虫、纹枯病、叶斑病为主，兼治条螟、玉米蚜、蓟马。①加强田间管理，及时中耕除草，合理施肥，增施磷、钾肥，以提高植株的抗逆性。②大喇叭口期用杀螟灵1号颗粒剂或1%1605颗粒剂、0．3%辛硫磷颗粒剂防治玉米螟。③用90％敌百虫1000倍液或50%敌敌畏2000倍液、50%辛硫磷乳油1000-2000倍液、40％乐果乳油1500倍液喷雾防治粘虫。④用70％甲基托布津或50％多菌灵可湿性粉剂500-800倍液喷雾防治纹枯病和叶斑病，也可用70％代森锰锌可湿性粉剂400-500倍液对病部喷雾或涂茎防治纹枯病。</p>', '<p>　苗期：以防治玉米蚜、蛀茎夜蛾、旋心虫、缺锌症为主。①结合间定苗拔除田间杂草，及时将杂草、病株集中烧毁，减少虫源。加强水肥管理，促进幼苗早发。②用40%乐果乳油或40％氧化乐果乳油2000-3000倍液喷雾，防治玉米蚜兼治灰飞虱。③局部发生蛀茎夜蛾、旋心虫的地块，用40%乐果乳油500倍液或90％敌百虫300倍液或50％敌敌畏400倍液灌根。④每亩用0．2％-0．3％硫酸锌溶液25-30公斤在衩?-5叶期叶面喷雾防治缺锌症，增强植株抗病能力。</p>', '2018-04-07 12:46:34');
