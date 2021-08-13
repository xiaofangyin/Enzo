package com.enzo.module_d.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 文 件 名: SubjectBean
 * 创 建 人: xiaofy
 * 创建日期: 2020/8/1
 * 邮   箱: xiaofywork@163.com
 */
public class SubjectBean implements Serializable {

    /**
     * rating : {"max":10,"average":5.3,"details":{"1":1179,"3":6305,"2":4458,"5":338,"4":1488},"stars":"30","min":0}
     * reviews_count : 465
     * videos : [{"source":{"literal":"iqiyi","pic":"http://img9.doubanio.com/f/movie/7c9e516e02c6fe445b6559c0dd2a705e8b17d1c9/pics/movie/video-iqiyi.png","name":"爱奇艺视频"},"sample_link":"http://www.iqiyi.com/v_19rr7q1nms.html?vfm=m_331_dbdy&fv=4904d94982104144a1548dd9040df241","video_id":"19rr7q1nms","need_pay":true}]
     * wish_count : 21641
     * original_title : Maze Runner: The Death Cure
     * blooper_urls : ["https://vt1.doubanio.com/202008012009/dc1c2def2c25d846a3e52f7a8dcf4a25/view/movie/M/302260637.mp4","https://vt1.doubanio.com/202008012009/ec914ca1f644f421f2ce7a1b0f0708b2/view/movie/M/302260450.mp4","https://vt1.doubanio.com/202008012009/d172ed139bee6f4e224c260ed02ec5fb/view/movie/M/302260600.mp4","https://vt1.doubanio.com/202008012009/25166fee354b48601035682f196bc8db/view/movie/M/302260837.mp4"]
     * collect_count : 181884
     * images : {"small":"http://img9.doubanio.com/view/photo/s_ratio_poster/public/p2508618114.jpg","large":"http://img9.doubanio.com/view/photo/s_ratio_poster/public/p2508618114.jpg","medium":"http://img9.doubanio.com/view/photo/s_ratio_poster/public/p2508618114.jpg"}
     * douban_site :
     * year : 2018
     * popular_comments : [{"rating":{"max":5,"value":3,"min":0},"useful_count":651,"author":{"uid":"tanknox","avatar":"http://img3.doubanio.com/icon/u41521823-22.jpg","signature":"不喝鸡汤，没有鸡血。","alt":"https://www.douban.com/people/tanknox/","id":"41521823","name":"TanKnoX"},"subject_id":"26004132","content":"有毒吧这个剧情\u2026\u2026男主读完兄弟的信之后十分感动，然后写下了女主的名字\u2026\u2026另外翻译全程yy得有点过分了\u2026\u2026","created_at":"2018-01-26 21:53:31","id":"1292584856"},{"rating":{"max":5,"value":3,"min":0},"useful_count":88,"author":{"uid":"164128873","avatar":"http://img9.doubanio.com/icon/u164128873-5.jpg","signature":"","alt":"https://www.douban.com/people/164128873/","id":"164128873","name":"不错我就是神"},"subject_id":"26004132","content":"我觉得虽然没有前两部好，但是还可以，剧情感觉不太好，可能编剧的思路我不懂？","created_at":"2018-01-26 23:34:04","id":"1313405045"},{"rating":{"max":5,"value":1,"min":0},"useful_count":176,"author":{"uid":"p2165","avatar":"http://img9.doubanio.com/icon/u51159028-14.jpg","signature":"微如牛虻之针   细若春雨之丝","alt":"https://www.douban.com/people/p2165/","id":"51159028","name":"二月鸟语"},"subject_id":"26004132","content":"一星半，3.2/10，我觉得资方真的是太有钱了！前面拍的昏昏欲睡，第三幕堆积特效狂花钱狂花钱狂花钱，满屏不把钱花完就不甘心的样子，明明十来分钟解决的事，非得使劲拉长到半个多小时，心疼编剧十秒钟～","created_at":"2018-01-25 03:21:49","id":"1312411670"},{"rating":{"max":5,"value":3,"min":0},"useful_count":107,"author":{"uid":"68632569","avatar":"http://img1.doubanio.com/icon/u68632569-9.jpg","signature":"","alt":"https://www.douban.com/people/68632569/","id":"68632569","name":"螢燈嫁晝"},"subject_id":"26004132","content":"我感觉热门有些人吐槽的点有点强行，虽说这系列离迷宫设定越来越远但人家是跟小说剧情走的，起吊机勾公交车且掉下来还无事你们看速8时候怎么不去问问飞机为什么会飞？不过缺点很明显，强行复活和强行死亡真的是没必要的情节以及反派最后的下场真的是没任何新意都不用猜！","created_at":"2018-01-26 16:42:26","id":"1313178033"}]
     * alt : https://movie.douban.com/subject/26004132/
     * id : 26004132
     * mobile_url : https://movie.douban.com/subject/26004132/mobile
     * photos_count : 418
     * pubdate : 2018-01-26
     * title : 移动迷宫3：死亡解药
     * do_count : null
     * has_video : true
     * share_url : http://m.douban.com/movie/subject/26004132
     * seasons_count : null
     * languages : ["英语"]
     * schedule_url :
     * writers : [{"avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1522030067.81.jpg","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1522030067.81.jpg","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1522030067.81.jpg"},"name_en":"T.S. Nowlin","name":"T·S·诺林","alt":"https://movie.douban.com/celebrity/1342902/","id":"1342902"},{"avatars":{"small":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1426087493.08.jpg","large":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1426087493.08.jpg","medium":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1426087493.08.jpg"},"name_en":"James Dashner","name":"詹姆斯·达什纳","alt":"https://movie.douban.com/celebrity/1333681/","id":"1333681"}]
     * pubdates : ["2018-01-26(美国)","2018-01-26(中国大陆)"]
     * website :
     * tags : ["科幻","美国","冒险","动作","反乌托邦","2018","小说改编","悬疑","惊悚","青春"]
     * has_schedule : false
     * durations : ["142分钟"]
     * genres : ["动作","科幻","冒险"]
     * collection : null
     * trailers : [{"medium":"http://img3.doubanio.com/img/trailer/medium/2512420390.jpg?","title":"中国预告片 (中文字幕)","subject_id":"26004132","alt":"https://movie.douban.com/trailer/227059/","small":"http://img3.doubanio.com/img/trailer/small/2512420390.jpg?","resource_url":"https://vt1.doubanio.com/202008012009/f6e407311631ffea33b5d06ff1fb2160/view/movie/M/302270059.mp4","id":"227059"},{"medium":"http://img9.doubanio.com/img/trailer/medium/2512246766.jpg?","title":"中国预告片：乐高版 (中文字幕)","subject_id":"26004132","alt":"https://movie.douban.com/trailer/226927/","small":"http://img9.doubanio.com/img/trailer/small/2512246766.jpg?","resource_url":"https://vt1.doubanio.com/202008012009/055985a216cf4ce7a89dc7127cf8678e/view/movie/M/302260927.mp4","id":"226927"},{"medium":"http://img3.doubanio.com/img/trailer/medium/2512030130.jpg?","title":"中国预告片：肾上腺素版 (中文字幕)","subject_id":"26004132","alt":"https://movie.douban.com/trailer/226763/","small":"http://img3.doubanio.com/img/trailer/small/2512030130.jpg?","resource_url":"https://vt1.doubanio.com/202008012009/3ffb72241f6d957285187e310d11133f/view/movie/M/302260763.mp4","id":"226763"},{"medium":"http://img9.doubanio.com/img/trailer/medium/2511722934.jpg?","title":"中国预告片 (中文字幕)","subject_id":"26004132","alt":"https://movie.douban.com/trailer/226701/","small":"http://img9.doubanio.com/img/trailer/small/2511722934.jpg?","resource_url":"https://vt1.doubanio.com/202008012009/0c5c31024c396a5c80e159360073db06/view/movie/M/302260701.mp4","id":"226701"}]
     * episodes_count : null
     * trailer_urls : ["https://vt1.doubanio.com/202008012009/f6e407311631ffea33b5d06ff1fb2160/view/movie/M/302270059.mp4","https://vt1.doubanio.com/202008012009/055985a216cf4ce7a89dc7127cf8678e/view/movie/M/302260927.mp4","https://vt1.doubanio.com/202008012009/3ffb72241f6d957285187e310d11133f/view/movie/M/302260763.mp4","https://vt1.doubanio.com/202008012009/0c5c31024c396a5c80e159360073db06/view/movie/M/302260701.mp4"]
     * has_ticket : false
     * bloopers : [{"medium":"http://img9.doubanio.com/img/trailer/medium/2511634245.jpg?","title":"花絮 (中文字幕)","subject_id":"26004132","alt":"https://movie.douban.com/trailer/226637/","small":"http://img9.doubanio.com/img/trailer/small/2511634245.jpg?","resource_url":"https://vt1.doubanio.com/202008012009/dc1c2def2c25d846a3e52f7a8dcf4a25/view/movie/M/302260637.mp4","id":"226637"},{"medium":"http://img3.doubanio.com/img/trailer/medium/2511379821.jpg?","title":"花絮：豆瓣电影专访《移动迷宫3》跑男团 (中文字幕)","subject_id":"26004132","alt":"https://movie.douban.com/trailer/226450/","small":"http://img3.doubanio.com/img/trailer/small/2511379821.jpg?","resource_url":"https://vt1.doubanio.com/202008012009/ec914ca1f644f421f2ce7a1b0f0708b2/view/movie/M/302260450.mp4","id":"226450"},{"medium":"http://img3.doubanio.com/img/trailer/medium/2511534902.jpg?","title":"MV：许魏洲献唱中推广曲《迷宫》 (中文字幕)","subject_id":"26004132","alt":"https://movie.douban.com/trailer/226600/","small":"http://img3.doubanio.com/img/trailer/small/2511534902.jpg?","resource_url":"https://vt1.doubanio.com/202008012009/d172ed139bee6f4e224c260ed02ec5fb/view/movie/M/302260600.mp4","id":"226600"},{"medium":"http://img1.doubanio.com/img/trailer/medium/2512140349.jpg?","title":"其它花絮：曼联特辑 (中文字幕)","subject_id":"26004132","alt":"https://movie.douban.com/trailer/226837/","small":"http://img1.doubanio.com/img/trailer/small/2512140349.jpg?","resource_url":"https://vt1.doubanio.com/202008012009/25166fee354b48601035682f196bc8db/view/movie/M/302260837.mp4","id":"226837"}]
     * clip_urls : ["https://vt1.doubanio.com/202008012009/6a563611870f2278c8320469def8577c/view/movie/M/302260558.mp4","https://vt1.doubanio.com/202008012009/d0f6b0722b7700b33a694ef0dfa1fe0d/view/movie/M/302250928.mp4"]
     * current_season : null
     * casts : [{"avatars":{"small":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p53688.jpg","large":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p53688.jpg","medium":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p53688.jpg"},"name_en":"Dylan O'Brien","name":"迪伦·奥布莱恩","alt":"https://movie.douban.com/celebrity/1314963/","id":"1314963"},{"avatars":{"small":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p13769.jpg","large":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p13769.jpg","medium":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p13769.jpg"},"name_en":"Kaya Scodelario","name":"卡雅·斯考达里奥","alt":"https://movie.douban.com/celebrity/1031178/","id":"1031178"},{"avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1395179688.42.jpg","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1395179688.42.jpg","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1395179688.42.jpg"},"name_en":"Ki Hong Lee","name":"李起弘","alt":"https://movie.douban.com/celebrity/1333684/","id":"1333684"},{"avatars":{"small":"http://img9.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1426088482.74.jpg","large":"http://img9.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1426088482.74.jpg","medium":"http://img9.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1426088482.74.jpg"},"name_en":"Thomas Brodie-Sangster","name":"托马斯·布罗迪-桑斯特","alt":"https://movie.douban.com/celebrity/1016669/","id":"1016669"}]
     * countries : ["美国"]
     * mainland_pubdate : 2018-01-26
     * photos : [{"thumb":"https://img3.doubanio.com/view/photo/m/public/p2519335363.jpg","image":"https://img3.doubanio.com/view/photo/l/public/p2519335363.jpg","cover":"https://img3.doubanio.com/view/photo/sqs/public/p2519335363.jpg","alt":"https://movie.douban.com/photos/photo/2519335363/","id":"2519335363","icon":"https://img3.doubanio.com/view/photo/s/public/p2519335363.jpg"},{"thumb":"https://img1.doubanio.com/view/photo/m/public/p2509193839.jpg","image":"https://img1.doubanio.com/view/photo/l/public/p2509193839.jpg","cover":"https://img1.doubanio.com/view/photo/sqs/public/p2509193839.jpg","alt":"https://movie.douban.com/photos/photo/2509193839/","id":"2509193839","icon":"https://img1.doubanio.com/view/photo/s/public/p2509193839.jpg"},{"thumb":"https://img1.doubanio.com/view/photo/m/public/p2540622109.jpg","image":"https://img1.doubanio.com/view/photo/l/public/p2540622109.jpg","cover":"https://img1.doubanio.com/view/photo/sqs/public/p2540622109.jpg","alt":"https://movie.douban.com/photos/photo/2540622109/","id":"2540622109","icon":"https://img1.doubanio.com/view/photo/s/public/p2540622109.jpg"},{"thumb":"https://img1.doubanio.com/view/photo/m/public/p2540622108.jpg","image":"https://img1.doubanio.com/view/photo/l/public/p2540622108.jpg","cover":"https://img1.doubanio.com/view/photo/sqs/public/p2540622108.jpg","alt":"https://movie.douban.com/photos/photo/2540622108/","id":"2540622108","icon":"https://img1.doubanio.com/view/photo/s/public/p2540622108.jpg"},{"thumb":"https://img1.doubanio.com/view/photo/m/public/p2540622107.jpg","image":"https://img1.doubanio.com/view/photo/l/public/p2540622107.jpg","cover":"https://img1.doubanio.com/view/photo/sqs/public/p2540622107.jpg","alt":"https://movie.douban.com/photos/photo/2540622107/","id":"2540622107","icon":"https://img1.doubanio.com/view/photo/s/public/p2540622107.jpg"},{"thumb":"https://img9.doubanio.com/view/photo/m/public/p2540622106.jpg","image":"https://img9.doubanio.com/view/photo/l/public/p2540622106.jpg","cover":"https://img9.doubanio.com/view/photo/sqs/public/p2540622106.jpg","alt":"https://movie.douban.com/photos/photo/2540622106/","id":"2540622106","icon":"https://img9.doubanio.com/view/photo/s/public/p2540622106.jpg"},{"thumb":"https://img1.doubanio.com/view/photo/m/public/p2533727399.jpg","image":"https://img1.doubanio.com/view/photo/l/public/p2533727399.jpg","cover":"https://img1.doubanio.com/view/photo/sqs/public/p2533727399.jpg","alt":"https://movie.douban.com/photos/photo/2533727399/","id":"2533727399","icon":"https://img1.doubanio.com/view/photo/s/public/p2533727399.jpg"},{"thumb":"https://img1.doubanio.com/view/photo/m/public/p2528936979.jpg","image":"https://img1.doubanio.com/view/photo/l/public/p2528936979.jpg","cover":"https://img1.doubanio.com/view/photo/sqs/public/p2528936979.jpg","alt":"https://movie.douban.com/photos/photo/2528936979/","id":"2528936979","icon":"https://img1.doubanio.com/view/photo/s/public/p2528936979.jpg"},{"thumb":"https://img1.doubanio.com/view/photo/m/public/p2528936977.jpg","image":"https://img1.doubanio.com/view/photo/l/public/p2528936977.jpg","cover":"https://img1.doubanio.com/view/photo/sqs/public/p2528936977.jpg","alt":"https://movie.douban.com/photos/photo/2528936977/","id":"2528936977","icon":"https://img1.doubanio.com/view/photo/s/public/p2528936977.jpg"},{"thumb":"https://img9.doubanio.com/view/photo/m/public/p2528936976.jpg","image":"https://img9.doubanio.com/view/photo/l/public/p2528936976.jpg","cover":"https://img9.doubanio.com/view/photo/sqs/public/p2528936976.jpg","alt":"https://movie.douban.com/photos/photo/2528936976/","id":"2528936976","icon":"https://img9.doubanio.com/view/photo/s/public/p2528936976.jpg"}]
     * summary : 托马斯（迪伦·奥布莱恩饰）率领的林间斗士在经历了迷宫逃脱和末日丧尸的生死考验后，终于迎来最后的正邪较量。由托马斯、纽特（托马斯·桑斯特饰）等人领军的营救团队，耗时三年筹划营救被抓走的米诺，却意外地发现米诺不在劫获的那截火车上。经调查得知，米诺深陷在WCKD组织的控制之中，托马斯和纽特毅然决定起身前往被称为“最后之都”的人类最后净土，更不惜利用背叛林间斗士投身WCKD的特蕾莎（卡雅·斯考达里奥饰）闯入WCKD，营救米诺和其他人。
     * clips : [{"medium":"http://img1.doubanio.com/img/trailer/medium/2511459538.jpg?","title":"片段 (中文字幕)","subject_id":"26004132","alt":"https://movie.douban.com/trailer/226558/","small":"http://img1.doubanio.com/img/trailer/small/2511459538.jpg?","resource_url":"https://vt1.doubanio.com/202008012009/6a563611870f2278c8320469def8577c/view/movie/M/302260558.mp4","id":"226558"},{"medium":"http://img3.doubanio.com/img/trailer/medium/2510150361.jpg?","title":"片段 (中文字幕)","subject_id":"26004132","alt":"https://movie.douban.com/trailer/225928/","small":"http://img3.doubanio.com/img/trailer/small/2510150361.jpg?","resource_url":"https://vt1.doubanio.com/202008012009/d0f6b0722b7700b33a694ef0dfa1fe0d/view/movie/M/302250928.mp4","id":"225928"}]
     * subtype : movie
     * directors : [{"avatars":{"small":"http://img9.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1417887954.94.jpg","large":"http://img9.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1417887954.94.jpg","medium":"http://img9.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1417887954.94.jpg"},"name_en":"Wes Ball","name":"韦斯·鲍尔","alt":"https://movie.douban.com/celebrity/1332723/","id":"1332723"}]
     * comments_count : 19887
     * popular_reviews : [{"rating":{"max":5,"value":2,"min":0},"title":"《移动迷宫3》：所谓的反乌托邦其实就是美国爽文","subject_id":"26004132","author":{"uid":"162000558","avatar":"http://img9.doubanio.com/icon/u162000558-4.jpg","signature":"","alt":"https://www.douban.com/people/162000558/","id":"162000558","name":"架空"},"summary":"经历了迷宫和丧尸的考验，《移动迷宫》系列终于在今天迎来了\u201c收官一跑\u201d。 作为三部曲的最后一部，《移动迷宫3：死亡解药》把四年间的所有谜团一次性解开，而以托马斯为首的林间地\u201c跑男团\u201d成员们，也将一边拯...","alt":"https://movie.douban.com/review/9103822/","id":"9103822"},{"rating":{"max":5,"value":2,"min":0},"title":"导演的脑回路就是个迷宫，走进去就出不来了","subject_id":"26004132","author":{"uid":"lingrui1995","avatar":"http://img1.doubanio.com/icon/u63688511-18.jpg","signature":"一个影迷","alt":"https://www.douban.com/people/lingrui1995/","id":"63688511","name":"墨尘"},"summary":"整个系列都没有移动，为什么在片名里写个\u201c移动\u201d？你考虑过联通和电信的感受吗？ 《移动迷宫1》没有移动，好歹还有迷宫。 《移动迷宫2》没有移动，没有迷宫，好歹还很二。 《移动迷宫3：死亡解药》是移动、迷宫...","alt":"https://movie.douban.com/review/9104389/","id":"9104389"},{"rating":{"max":5,"value":3,"min":0},"title":"只为纪念我的newt","subject_id":"26004132","author":{"uid":"74010591","avatar":"http://img1.doubanio.com/icon/user_normal.jpg","signature":"","alt":"https://www.douban.com/people/74010591/","id":"74010591","name":"开到荼蘼花事了"},"summary":"电影给了三星，完全是冲着newt这个角色和桑总的面子嗯。 开始看移动迷宫是男票推荐的，第一部里面就发现newt比男一更有魅力，然后就意犹未尽的看了第二部，可惜第二部里面给newt的戏份略少，感觉没看过瘾，当时距...","alt":"https://movie.douban.com/review/9106985/","id":"9106985"},{"rating":{"max":5,"value":3,"min":0},"title":"你对《移动迷宫》系列探讨的主旨\u201c救朋友还是救世界\u201d有何看法？","subject_id":"26004132","author":{"uid":"151402596","avatar":"http://img9.doubanio.com/icon/u151402596-4.jpg","signature":"","alt":"https://www.douban.com/people/151402596/","id":"151402596","name":"Agon"},"summary":"可以试试回答另一个问题：如果牺牲五个人，能救五百万人，该不该牺牲？ 如果是五个你的至亲和五百万流氓呢？ 如果是五个流氓和五百万非洲文盲呢？ 如果是五个非洲文盲和五百万世界权贵呢？ 如果是五个世界权贵和...","alt":"https://movie.douban.com/review/9110398/","id":"9110398"},{"rating":{"max":5,"value":4,"min":0},"title":"剧透吐槽千里救minho之再见了我的Newt","subject_id":"26004132","author":{"uid":"AnnaDoyle","avatar":"http://img3.doubanio.com/icon/u30263572-12.jpg","signature":"Don't blink.","alt":"https://www.douban.com/people/AnnaDoyle/","id":"30263572","name":"AnnaDoyle"},"summary":"1. 第三部别名：千里救minho 2. 可怜的minho 3. 我不太清楚小说里的泰瑞沙，但上一部她已经没啥存在感（相比Brenda）不拉好感了这一部看到更没啥感觉了 4. 这就是一个看脸的世界，谁能留Newt的发型还能保持帅气？...","alt":"https://movie.douban.com/review/9088112/","id":"9088112"},{"rating":{"max":5,"value":1,"min":0},"title":"难道该死的都是好人？","subject_id":"26004132","author":{"uid":"173187137","avatar":"http://img3.doubanio.com/icon/u173187137-1.jpg","signature":"","alt":"https://www.douban.com/people/173187137/","id":"173187137","name":"moonlit bear"},"summary":"这是我第一次写影评。          我以前从未写过影评。这也是我第一次，在还没看完一部电影时就气得恨不得马上甩凳子走人。          就在半个小时之前，我看完了这部电影。电影的内容很简单，讲的就是当人类面对...","alt":"https://movie.douban.com/review/9107630/","id":"9107630"},{"rating":{"max":5,"value":2,"min":0},"title":"对着天空大吼一声：\u201c我是主角！\u201d","subject_id":"26004132","author":{"uid":"56453834","avatar":"http://img3.doubanio.com/icon/u56453834-2.jpg","signature":"","alt":"https://www.douban.com/people/56453834/","id":"56453834","name":"ltheyes"},"summary":"叫迷宫没有迷宫也就算了，所有配角和剧情统统给主角光环让路，最后还给个360度大特写，简直怀疑男主跟导演有py交易\u2026\u2026     我是主角托马斯。 别问为什么我是主角，反正我自带光环。  前1+2集里，全球又毁灭了，...","alt":"https://movie.douban.com/review/9103831/","id":"9103831"},{"rating":{"max":5,"value":3,"min":0},"title":"兄弟情深的基情跑男，当然要烧死异性恋！","subject_id":"26004132","author":{"uid":"65569388","avatar":"http://img9.doubanio.com/icon/u65569388-5.jpg","signature":"","alt":"https://www.douban.com/people/65569388/","id":"65569388","name":"大海里的针"},"summary":"以前大海总觉得日本动漫中二病泛滥，动不动就是青少年主角拯救世界。最近看了新上映的《移动迷宫3》，发现美国人中二起来比日本人是有过之而无不及。 《移动迷宫3：死亡解药》是这个系列的最终章，影片质量和同样...","alt":"https://movie.douban.com/review/9115993/","id":"9115993"},{"rating":{"max":5,"value":3,"min":0},"title":"死亡没有解药","subject_id":"26004132","author":{"uid":"163879289","avatar":"http://img3.doubanio.com/icon/u163879289-3.jpg","signature":"","alt":"https://www.douban.com/people/163879289/","id":"163879289","name":"三只兔子"},"summary":"相对于第一篇差了好多 全部看下来除了剧情不知所云 还有拖拉的讲述 托马斯率领的林间斗士在经历了迷宫逃脱和末日丧尸的生死考验后，终于迎来最后的正邪较量。由托马斯等人领军的营救团队，耗时三年筹划营救被抓走...","alt":"https://movie.douban.com/review/9301548/","id":"9301548"},{"rating":{"max":5,"value":3,"min":0},"title":"对于大家吐槽的三观问题的个人浅见","subject_id":"26004132","author":{"uid":"164445055","avatar":"http://img3.doubanio.com/icon/u164445055-1.jpg","signature":"","alt":"https://www.douban.com/people/164445055/","id":"164445055","name":"Netkr-网客"},"summary":"大家热议的本片价值观问题集中在主角为什么不乖乖配合WCKD公司研发解药解救全人类皆大欢喜以及难民愚蠢毁灭城市耽误解药研发。 首先，男主之前是为WCKD公司工作的，这点大家忘了吗?说明男主曾经信任该公司可以研...","alt":"https://movie.douban.com/review/9122195/","id":"9122195"}]
     * ratings_count : 74459
     * aka : ["移动迷宫3","死亡解药","The Death Cure"]
     */

    private RatingBean rating;
    private int reviews_count;
    private int wish_count;
    private String original_title;
    private int collect_count;
    private ImagesBean images;
    private String douban_site;
    private String year;
    private String alt;
    private String id;
    private String mobile_url;
    private int photos_count;
    private String pubdate;
    private String title;
    private Object do_count;
    private boolean has_video;
    private String share_url;
    private Object seasons_count;
    private String schedule_url;
    private String website;
    private boolean has_schedule;
    private Object collection;
    private Object episodes_count;
    private boolean has_ticket;
    private Object current_season;
    private String mainland_pubdate;
    private String summary;
    private String subtype;
    private int comments_count;
    private int ratings_count;
    private List<VideosBean> videos;
    private List<String> blooper_urls;
    private List<PopularCommentsBean> popular_comments;
    private List<String> languages;
    private List<WritersBean> writers;
    private List<String> pubdates;
    private List<String> tags;
    private List<String> durations;
    private List<String> genres;
    private List<TrailersBean> trailers;
    private List<String> trailer_urls;
    private List<BloopersBean> bloopers;
    private List<String> clip_urls;
    private List<CastsBean> casts;
    private List<String> countries;
    private List<PhotosBean> photos;
    private List<ClipsBean> clips;
    private List<DirectorsBean> directors;
    private List<PopularReviewsBean> popular_reviews;
    private List<String> aka;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getDouban_site() {
        return douban_site;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public int getPhotos_count() {
        return photos_count;
    }

    public void setPhotos_count(int photos_count) {
        this.photos_count = photos_count;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDo_count() {
        return do_count;
    }

    public void setDo_count(Object do_count) {
        this.do_count = do_count;
    }

    public boolean isHas_video() {
        return has_video;
    }

    public void setHas_video(boolean has_video) {
        this.has_video = has_video;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public Object getSeasons_count() {
        return seasons_count;
    }

    public void setSeasons_count(Object seasons_count) {
        this.seasons_count = seasons_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isHas_schedule() {
        return has_schedule;
    }

    public void setHas_schedule(boolean has_schedule) {
        this.has_schedule = has_schedule;
    }

    public Object getCollection() {
        return collection;
    }

    public void setCollection(Object collection) {
        this.collection = collection;
    }

    public Object getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(Object episodes_count) {
        this.episodes_count = episodes_count;
    }

    public boolean isHas_ticket() {
        return has_ticket;
    }

    public void setHas_ticket(boolean has_ticket) {
        this.has_ticket = has_ticket;
    }

    public Object getCurrent_season() {
        return current_season;
    }

    public void setCurrent_season(Object current_season) {
        this.current_season = current_season;
    }

    public String getMainland_pubdate() {
        return mainland_pubdate;
    }

    public void setMainland_pubdate(String mainland_pubdate) {
        this.mainland_pubdate = mainland_pubdate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public List<VideosBean> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosBean> videos) {
        this.videos = videos;
    }

    public List<String> getBlooper_urls() {
        return blooper_urls;
    }

    public void setBlooper_urls(List<String> blooper_urls) {
        this.blooper_urls = blooper_urls;
    }

    public List<PopularCommentsBean> getPopular_comments() {
        return popular_comments;
    }

    public void setPopular_comments(List<PopularCommentsBean> popular_comments) {
        this.popular_comments = popular_comments;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<WritersBean> getWriters() {
        return writers;
    }

    public void setWriters(List<WritersBean> writers) {
        this.writers = writers;
    }

    public List<String> getPubdates() {
        return pubdates;
    }

    public void setPubdates(List<String> pubdates) {
        this.pubdates = pubdates;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getDurations() {
        return durations;
    }

    public void setDurations(List<String> durations) {
        this.durations = durations;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<TrailersBean> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<TrailersBean> trailers) {
        this.trailers = trailers;
    }

    public List<String> getTrailer_urls() {
        return trailer_urls;
    }

    public void setTrailer_urls(List<String> trailer_urls) {
        this.trailer_urls = trailer_urls;
    }

    public List<BloopersBean> getBloopers() {
        return bloopers;
    }

    public void setBloopers(List<BloopersBean> bloopers) {
        this.bloopers = bloopers;
    }

    public List<String> getClip_urls() {
        return clip_urls;
    }

    public void setClip_urls(List<String> clip_urls) {
        this.clip_urls = clip_urls;
    }

    public List<CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<CastsBean> casts) {
        this.casts = casts;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<PhotosBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosBean> photos) {
        this.photos = photos;
    }

    public List<ClipsBean> getClips() {
        return clips;
    }

    public void setClips(List<ClipsBean> clips) {
        this.clips = clips;
    }

    public List<DirectorsBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorsBean> directors) {
        this.directors = directors;
    }

    public List<PopularReviewsBean> getPopular_reviews() {
        return popular_reviews;
    }

    public void setPopular_reviews(List<PopularReviewsBean> popular_reviews) {
        this.popular_reviews = popular_reviews;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public static class RatingBean {
        /**
         * max : 10
         * average : 5.3
         * details : {"1":1179,"3":6305,"2":4458,"5":338,"4":1488}
         * stars : 30
         * min : 0
         */

        private int max;
        private double average;
        private DetailsBean details;
        private String stars;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public DetailsBean getDetails() {
            return details;
        }

        public void setDetails(DetailsBean details) {
            this.details = details;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public static class DetailsBean {
            /**
             * 1 : 1179.0
             * 3 : 6305.0
             * 2 : 4458.0
             * 5 : 338.0
             * 4 : 1488.0
             */

            @SerializedName("1")
            private double _$1;
            @SerializedName("3")
            private double _$3;
            @SerializedName("2")
            private double _$2;
            @SerializedName("5")
            private double _$5;
            @SerializedName("4")
            private double _$4;

            public double get_$1() {
                return _$1;
            }

            public void set_$1(double _$1) {
                this._$1 = _$1;
            }

            public double get_$3() {
                return _$3;
            }

            public void set_$3(double _$3) {
                this._$3 = _$3;
            }

            public double get_$2() {
                return _$2;
            }

            public void set_$2(double _$2) {
                this._$2 = _$2;
            }

            public double get_$5() {
                return _$5;
            }

            public void set_$5(double _$5) {
                this._$5 = _$5;
            }

            public double get_$4() {
                return _$4;
            }

            public void set_$4(double _$4) {
                this._$4 = _$4;
            }
        }
    }

    public static class ImagesBean {
        /**
         * small : http://img9.doubanio.com/view/photo/s_ratio_poster/public/p2508618114.jpg
         * large : http://img9.doubanio.com/view/photo/s_ratio_poster/public/p2508618114.jpg
         * medium : http://img9.doubanio.com/view/photo/s_ratio_poster/public/p2508618114.jpg
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class VideosBean {
        /**
         * source : {"literal":"iqiyi","pic":"http://img9.doubanio.com/f/movie/7c9e516e02c6fe445b6559c0dd2a705e8b17d1c9/pics/movie/video-iqiyi.png","name":"爱奇艺视频"}
         * sample_link : http://www.iqiyi.com/v_19rr7q1nms.html?vfm=m_331_dbdy&fv=4904d94982104144a1548dd9040df241
         * video_id : 19rr7q1nms
         * need_pay : true
         */

        private SourceBean source;
        private String sample_link;
        private String video_id;
        private boolean need_pay;

        public SourceBean getSource() {
            return source;
        }

        public void setSource(SourceBean source) {
            this.source = source;
        }

        public String getSample_link() {
            return sample_link;
        }

        public void setSample_link(String sample_link) {
            this.sample_link = sample_link;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public boolean isNeed_pay() {
            return need_pay;
        }

        public void setNeed_pay(boolean need_pay) {
            this.need_pay = need_pay;
        }

        public static class SourceBean {
            /**
             * literal : iqiyi
             * pic : http://img9.doubanio.com/f/movie/7c9e516e02c6fe445b6559c0dd2a705e8b17d1c9/pics/movie/video-iqiyi.png
             * name : 爱奇艺视频
             */

            private String literal;
            private String pic;
            private String name;

            public String getLiteral() {
                return literal;
            }

            public void setLiteral(String literal) {
                this.literal = literal;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class PopularCommentsBean {
        /**
         * rating : {"max":5,"value":3,"min":0}
         * useful_count : 651
         * author : {"uid":"tanknox","avatar":"http://img3.doubanio.com/icon/u41521823-22.jpg","signature":"不喝鸡汤，没有鸡血。","alt":"https://www.douban.com/people/tanknox/","id":"41521823","name":"TanKnoX"}
         * subject_id : 26004132
         * content : 有毒吧这个剧情……男主读完兄弟的信之后十分感动，然后写下了女主的名字……另外翻译全程yy得有点过分了……
         * created_at : 2018-01-26 21:53:31
         * id : 1292584856
         */

        private RatingBeanX rating;
        private int useful_count;
        private AuthorBean author;
        private String subject_id;
        private String content;
        private String created_at;
        private String id;

        public RatingBeanX getRating() {
            return rating;
        }

        public void setRating(RatingBeanX rating) {
            this.rating = rating;
        }

        public int getUseful_count() {
            return useful_count;
        }

        public void setUseful_count(int useful_count) {
            this.useful_count = useful_count;
        }

        public AuthorBean getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBean author) {
            this.author = author;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class RatingBeanX {
            /**
             * max : 5
             * value : 3.0
             * min : 0
             */

            private int max;
            private double value;
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class AuthorBean {
            /**
             * uid : tanknox
             * avatar : http://img3.doubanio.com/icon/u41521823-22.jpg
             * signature : 不喝鸡汤，没有鸡血。
             * alt : https://www.douban.com/people/tanknox/
             * id : 41521823
             * name : TanKnoX
             */

            private String uid;
            private String avatar;
            private String signature;
            private String alt;
            private String id;
            private String name;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class WritersBean {
        /**
         * avatars : {"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1522030067.81.jpg","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1522030067.81.jpg","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1522030067.81.jpg"}
         * name_en : T.S. Nowlin
         * name : T·S·诺林
         * alt : https://movie.douban.com/celebrity/1342902/
         * id : 1342902
         */

        private AvatarsBean avatars;
        private String name_en;
        private String name;
        private String alt;
        private String id;

        public AvatarsBean getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBean {
            /**
             * small : http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1522030067.81.jpg
             * large : http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1522030067.81.jpg
             * medium : http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1522030067.81.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }

    public static class TrailersBean {
        /**
         * medium : http://img3.doubanio.com/img/trailer/medium/2512420390.jpg?
         * title : 中国预告片 (中文字幕)
         * subject_id : 26004132
         * alt : https://movie.douban.com/trailer/227059/
         * small : http://img3.doubanio.com/img/trailer/small/2512420390.jpg?
         * resource_url : https://vt1.doubanio.com/202008012009/f6e407311631ffea33b5d06ff1fb2160/view/movie/M/302270059.mp4
         * id : 227059
         */

        private String medium;
        private String title;
        private String subject_id;
        private String alt;
        private String small;
        private String resource_url;
        private String id;

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getResource_url() {
            return resource_url;
        }

        public void setResource_url(String resource_url) {
            this.resource_url = resource_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class BloopersBean {
        /**
         * medium : http://img9.doubanio.com/img/trailer/medium/2511634245.jpg?
         * title : 花絮 (中文字幕)
         * subject_id : 26004132
         * alt : https://movie.douban.com/trailer/226637/
         * small : http://img9.doubanio.com/img/trailer/small/2511634245.jpg?
         * resource_url : https://vt1.doubanio.com/202008012009/dc1c2def2c25d846a3e52f7a8dcf4a25/view/movie/M/302260637.mp4
         * id : 226637
         */

        private String medium;
        private String title;
        private String subject_id;
        private String alt;
        private String small;
        private String resource_url;
        private String id;

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getResource_url() {
            return resource_url;
        }

        public void setResource_url(String resource_url) {
            this.resource_url = resource_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class CastsBean {
        /**
         * avatars : {"small":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p53688.jpg","large":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p53688.jpg","medium":"http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p53688.jpg"}
         * name_en : Dylan O'Brien
         * name : 迪伦·奥布莱恩
         * alt : https://movie.douban.com/celebrity/1314963/
         * id : 1314963
         */

        private AvatarsBeanX avatars;
        private String name_en;
        private String name;
        private String alt;
        private String id;

        public AvatarsBeanX getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBeanX avatars) {
            this.avatars = avatars;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBeanX {
            /**
             * small : http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p53688.jpg
             * large : http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p53688.jpg
             * medium : http://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p53688.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }

    public static class PhotosBean {
        /**
         * thumb : https://img3.doubanio.com/view/photo/m/public/p2519335363.jpg
         * image : https://img3.doubanio.com/view/photo/l/public/p2519335363.jpg
         * cover : https://img3.doubanio.com/view/photo/sqs/public/p2519335363.jpg
         * alt : https://movie.douban.com/photos/photo/2519335363/
         * id : 2519335363
         * icon : https://img3.doubanio.com/view/photo/s/public/p2519335363.jpg
         */

        private String thumb;
        private String image;
        private String cover;
        private String alt;
        private String id;
        private String icon;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class ClipsBean {
        /**
         * medium : http://img1.doubanio.com/img/trailer/medium/2511459538.jpg?
         * title : 片段 (中文字幕)
         * subject_id : 26004132
         * alt : https://movie.douban.com/trailer/226558/
         * small : http://img1.doubanio.com/img/trailer/small/2511459538.jpg?
         * resource_url : https://vt1.doubanio.com/202008012009/6a563611870f2278c8320469def8577c/view/movie/M/302260558.mp4
         * id : 226558
         */

        private String medium;
        private String title;
        private String subject_id;
        private String alt;
        private String small;
        private String resource_url;
        private String id;

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getResource_url() {
            return resource_url;
        }

        public void setResource_url(String resource_url) {
            this.resource_url = resource_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class DirectorsBean {
        /**
         * avatars : {"small":"http://img9.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1417887954.94.jpg","large":"http://img9.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1417887954.94.jpg","medium":"http://img9.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1417887954.94.jpg"}
         * name_en : Wes Ball
         * name : 韦斯·鲍尔
         * alt : https://movie.douban.com/celebrity/1332723/
         * id : 1332723
         */

        private AvatarsBeanXX avatars;
        private String name_en;
        private String name;
        private String alt;
        private String id;

        public AvatarsBeanXX getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBeanXX avatars) {
            this.avatars = avatars;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBeanXX {
            /**
             * small : http://img9.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1417887954.94.jpg
             * large : http://img9.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1417887954.94.jpg
             * medium : http://img9.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1417887954.94.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }

    public static class PopularReviewsBean {
        /**
         * rating : {"max":5,"value":2,"min":0}
         * title : 《移动迷宫3》：所谓的反乌托邦其实就是美国爽文
         * subject_id : 26004132
         * author : {"uid":"162000558","avatar":"http://img9.doubanio.com/icon/u162000558-4.jpg","signature":"","alt":"https://www.douban.com/people/162000558/","id":"162000558","name":"架空"}
         * summary : 经历了迷宫和丧尸的考验，《移动迷宫》系列终于在今天迎来了“收官一跑”。 作为三部曲的最后一部，《移动迷宫3：死亡解药》把四年间的所有谜团一次性解开，而以托马斯为首的林间地“跑男团”成员们，也将一边拯...
         * alt : https://movie.douban.com/review/9103822/
         * id : 9103822
         */

        private RatingBeanXX rating;
        private String title;
        private String subject_id;
        private AuthorBeanX author;
        private String summary;
        private String alt;
        private String id;

        public RatingBeanXX getRating() {
            return rating;
        }

        public void setRating(RatingBeanXX rating) {
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public AuthorBeanX getAuthor() {
            return author;
        }

        public void setAuthor(AuthorBeanX author) {
            this.author = author;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class RatingBeanXX {
            /**
             * max : 5
             * value : 2.0
             * min : 0
             */

            private int max;
            private double value;
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class AuthorBeanX {
            /**
             * uid : 162000558
             * avatar : http://img9.doubanio.com/icon/u162000558-4.jpg
             * signature :
             * alt : https://www.douban.com/people/162000558/
             * id : 162000558
             * name : 架空
             */

            private String uid;
            private String avatar;
            private String signature;
            private String alt;
            private String id;
            private String name;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
