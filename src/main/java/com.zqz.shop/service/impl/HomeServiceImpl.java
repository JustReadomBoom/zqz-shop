package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.*;
import com.zqz.shop.mapper.*;
import com.zqz.shop.service.HomeService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.zqz.shop.entity.table.AdTableDef.AD;
import static com.zqz.shop.entity.table.ArticleTableDef.ARTICLE;
import static com.zqz.shop.entity.table.BrandTableDef.BRAND;
import static com.zqz.shop.entity.table.CategoryTableDef.CATEGORY;
import static com.zqz.shop.entity.table.GoodsTableDef.GOODS;
import static com.zqz.shop.entity.table.GrouponRulesTableDef.GROUPON_RULES;
import static com.zqz.shop.entity.table.TopicTableDef.TOPIC;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: HomeServiceImpl
 * @Date: Created in 15:57 2023-8-4
 */
@Service
@Slf4j
public class HomeServiceImpl implements HomeService {
    @Resource
    private AdMapper adMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private BrandMapper brandMapper;
    @Resource
    private TopicMapper topicMapper;
    @Resource
    private GrouponRulesMapper grouponRulesMapper;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public Object doQueryIndex(Integer userId) {
        try {
            Map<String, Object> data = new HashMap<>(10);

            Callable<List> bannerListCallable = this::queryAdList;
            Callable<List> articleListCallable = this::queryArticleList;
            Callable<List> channelListCallable = this::queryChannel;
            Callable<List> newGoodsListCallable = this::queryNewGoods;
            Callable<List> hotGoodsListCallable = this::queryHotGoods;
            Callable<List> brandListCallable = this::queryBrands;
            Callable<List> topicListCallable = this::queryTopics;
            Callable<List> grouponRulesListCallable = this::queryGrouponRules;
            Callable<List> floorGoodsListCallable = this::queryFloorGoodsList;

            FutureTask<List> bannerTask = new FutureTask<>(bannerListCallable);
            FutureTask<List> articleTask = new FutureTask<>(articleListCallable);
            FutureTask<List> channelTask = new FutureTask<>(channelListCallable);
            FutureTask<List> newGoodsTask = new FutureTask<>(newGoodsListCallable);
            FutureTask<List> hotGoodsTask = new FutureTask<>(hotGoodsListCallable);
            FutureTask<List> brandsTask = new FutureTask<>(brandListCallable);
            FutureTask<List> topicsTask = new FutureTask<>(topicListCallable);
            FutureTask<List> grouponRulesTask = new FutureTask<>(grouponRulesListCallable);
            FutureTask<List> floorGoodsTask = new FutureTask<>(floorGoodsListCallable);

            executor.submit(bannerTask);
            executor.submit(articleTask);
            executor.submit(channelTask);
            executor.submit(newGoodsTask);
            executor.submit(hotGoodsTask);
            executor.submit(brandsTask);
            executor.submit(topicsTask);
            executor.submit(grouponRulesTask);
            executor.submit(floorGoodsTask);

            data.put("banner", bannerTask.get());
            data.put("articles", articleTask.get());
            data.put("channel", channelTask.get());
            data.put("newGoodsList", newGoodsTask.get());
            data.put("hotGoodsList", hotGoodsTask.get());
            data.put("brandList", brandsTask.get());
            data.put("topicList", topicsTask.get());
            data.put("grouponList", grouponRulesTask.get());
            data.put("floorGoodsList", floorGoodsTask.get());
            return ResponseUtil.ok(data);

        } catch (Exception e) {
            log.error("doQueryIndex error:{}", e.getMessage());
            return "fail";
        } finally {
            executor.shutdown();
        }
    }


    private List<Ad> queryAdList() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(AD.POSITION.eq(1))
                .and(AD.DELETED.eq(false))
                .and(AD.ENABLED.eq(true));
        return adMapper.selectListByQuery(wrapper);
    }

    private List<Article> queryArticleList() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(ARTICLE.DELETED.eq(false))
                .orderBy(ARTICLE.ADD_TIME.desc());
        return articleMapper.paginateWithRelations(1, 5, wrapper).getRecords();
    }

    private List<Category> queryChannel() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(CATEGORY.LEVEL.eq("L1"))
                .and(CATEGORY.DELETED.eq(false));
        return categoryMapper.paginateWithRelations(1, 9, wrapper).getRecords();
    }

    private List<Goods> queryNewGoods() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(GOODS.IS_NEW.eq(true))
                .and(GOODS.IS_ON_SALE.eq(true))
                .and(GOODS.DELETED.eq(false))
                .orderBy(GOODS.ADD_TIME.desc());
        return goodsMapper.paginateWithRelations(1, 20, wrapper).getRecords();
    }

    private List<Goods> queryHotGoods() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(GOODS.IS_HOT.eq(true))
                .and(GOODS.IS_ON_SALE.eq(true))
                .and(GOODS.DELETED.eq(false))
                .orderBy(GOODS.BROWSE.desc());
        return goodsMapper.paginateWithRelations(1, 20, wrapper).getRecords();
    }


    private List<Brand> queryBrands() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(BRAND.DELETED.eq(false))
                .orderBy(BRAND.ADD_TIME.desc());
        return brandMapper.paginateWithRelations(1, 6, wrapper).getRecords();
    }

    private List<Topic> queryTopics() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(TOPIC.DELETED.eq(false))
                .orderBy(TOPIC.ADD_TIME.desc());
        return topicMapper.paginateWithRelations(1, 6, wrapper).getRecords();
    }

    private List<Map<String, Object>> queryGrouponRules() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(GROUPON_RULES.DELETED.eq(false))
                .orderBy(GROUPON_RULES.ADD_TIME.desc());
        List<GrouponRules> rulesList = grouponRulesMapper.paginateWithRelations(1, 6, wrapper).getRecords();
        List<Map<String, Object>> grouponList = new ArrayList<>(rulesList.size());
        if (CollectionUtil.isNotEmpty(rulesList)) {
            for (GrouponRules r : rulesList) {
                int goodsId = r.getGoodsId().intValue();
                Goods goods = goodsMapper.selectOneById(goodsId);
                if (ObjectUtil.isEmpty(goods)) {
                    continue;
                }
                Map<String, Object> item = new HashMap<>();
                item.put("goods", goods);
                item.put("groupon_price", goods.getRetailPrice().subtract(r.getDiscount()));
                item.put("groupon_member", r.getDiscountMember());
                grouponList.add(item);
            }
        }
        return grouponList;
    }


    private List<Map> queryFloorGoodsList() {
        List<Map> categoryList = new ArrayList<>();
        QueryWrapper wrapper1 = QueryWrapper.create();
        wrapper1.select()
                .where(CATEGORY.LEVEL.eq("L1"))
                .and(CATEGORY.NAME.ne("推荐"))
                .and(CATEGORY.DELETED.eq(false));
        List<Category> categories = categoryMapper.paginateWithRelations(1, 8, wrapper1).getRecords();
        log.info("categories = {}", JSONUtil.toJsonStr(categories));
        if (CollectionUtil.isNotEmpty(categories)) {
            for (Category category : categories) {
                QueryWrapper wrapper2 = QueryWrapper.create();
                wrapper2.select()
                        .where(CATEGORY.PID.eq(category.getId()))
                        .and(CATEGORY.DELETED.eq(false));
                List<Category> categories2 = categoryMapper.selectListByQuery(wrapper2);
                log.info("categories2 = {}", JSONUtil.toJsonStr(categories2));
                List<Goods> categoryGoods;
                if (CollectionUtil.isNotEmpty(categories2)) {
                    List<Integer> idList = categories2.stream().map(Category::getId).collect(Collectors.toList());
                    log.info("categoryIdList = {}", JSONUtil.toJsonStr(idList));
                    QueryWrapper wrapper3 = QueryWrapper.create();
                    wrapper3.select()
                            .where(GOODS.CATEGORY_ID.in(idList))
                            .and(GOODS.IS_ON_SALE.eq(true))
                            .and(GOODS.DELETED.eq(false))
                            .orderBy(GOODS.SORT_ORDER.asc());
                    categoryGoods = goodsMapper.paginateWithRelations(1, 20, wrapper3).getRecords();
                } else {
                    categoryGoods = new ArrayList<>();
                }
                log.info("categoryGoods = {}", JSONUtil.toJsonStr(categoryGoods));
                Map<String, Object> catGoods = new HashMap<>(3);
                catGoods.put("id", category.getId());
                catGoods.put("name", category.getName());
                catGoods.put("goodsList", categoryGoods);
                categoryList.add(catGoods);
            }
        }
        return categoryList;
    }
}
