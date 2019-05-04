package com.jacky.labeauty.ui.function.me.discount

import android.content.Context
import android.database.DataSetObserver
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.jacky.labeauty.R
import com.jacky.labeauty.support.util.AndroidUtil
import com.jacky.labeauty.ui.adapter.page.TabLayoutAdapter
import com.jacky.labeauty.ui.function.me.prize.MyDiscountFragment
import com.jacky.labeauty.ui.inner.arch.MySupportFragment
import kotlinx.android.synthetic.main.fragment_my_prize.*
import net.lucode.hackware.magicindicator.NavigatorHelper
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.abs.IPagerNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

class MyPrizeFragment : MySupportFragment() {
    private val tabNames = ArrayList<String>(2)
    private val fragments = ArrayList<Fragment>(2)
    private lateinit var navigator: CommonNavigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_prize, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleView.setLeftAction(View.OnClickListener { activity!!.finish() })

        tabNames.add("优惠券")
        tabNames.add("实物奖品")
        fragments.add(MyDiscountFragment())
        fragments.add(MyDiscountFragment())

        val commonNavigator = object : CommonNavigator(context!!) {
            override fun onAfterInitTitleView() {
                val width = AndroidUtil.getScreenWidth() * 1.0f / 2
                val height = AndroidUtil.dip2px(45f)
                val layoutParams = LinearLayout.LayoutParams(width.toInt(), height.toInt())
                for (i in 0 until 2) {
                    val pagerTitleView = navigator.getPagerTitleView(i) as SimplePagerTitleView
                    pagerTitleView.layoutParams = layoutParams
                }
            }
        }
        navigator = commonNavigator
        commonNavigator.scrollPivotX = 0.25f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount() = 2

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
//                val paint = simplePagerTitleView.paint
//                paint.isFakeBoldText = true
                simplePagerTitleView.text = tabNames[index]
                simplePagerTitleView.normalColor = Color.parseColor("#8b8b8d")
                simplePagerTitleView.selectedColor = ContextCompat.getColor(context, R.color.labe_blue)
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                simplePagerTitleView.setOnClickListener { viewPager.currentItem = index }
                simplePagerTitleView.height = AndroidUtil.dip2px(55F).toInt()
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = object : LinePagerIndicator(context) {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)

                    }
                }
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineWidth = AndroidUtil.dip2px(65F)
                indicator.lineHeight = AndroidUtil.dip2px(4F)
                indicator.setColors(ContextCompat.getColor(context, R.color.labe_blue))
                return indicator
            }
        }

        magic_indicator.navigator = commonNavigator

        val tabLayoutAdapter = TabLayoutAdapter(fragmentManager, tabNames, fragments as List<Fragment>)
        viewPager.adapter = tabLayoutAdapter
        ViewPagerHelper.bind(magic_indicator, viewPager)
    }

    open class CommonNavigator(context: Context) : FrameLayout(context), IPagerNavigator, NavigatorHelper.OnNavigatorScrollListener {
        private var mScrollView: HorizontalScrollView? = null
        var titleContainer: LinearLayout? = null
            private set
        private var mIndicatorContainer: LinearLayout? = null
        var pagerIndicator: IPagerIndicator? = null
            private set
        var adapter: CommonNavigatorAdapter? = null
            set(adapter) {
                if (this.adapter !== adapter) {
                    if (this.adapter != null) {
                        this.adapter!!.unregisterDataSetObserver(this.mObserver)
                    }

                    field = adapter
                    if (this.adapter != null) {
                        this.adapter!!.registerDataSetObserver(this.mObserver)
                        this.mNavigatorHelper.totalCount = this.adapter!!.count
                        if (this.titleContainer != null) {
                            this.adapter!!.notifyDataSetChanged()
                        }
                    } else {
                        this.mNavigatorHelper.totalCount = 0
                        this.init()
                    }

                }
            }
        private val mNavigatorHelper = NavigatorHelper()
        var isAdjustMode: Boolean = false
        var isEnablePivotScroll: Boolean = false
        var scrollPivotX = 0.5f
        var isSmoothScroll = true
        var isFollowTouch = true
        var rightPadding: Int = 0
        var leftPadding: Int = 0
        var isIndicatorOnTop: Boolean = false
        var isSkimOver: Boolean = false
            set(skimOver) {
                field = skimOver
                this.mNavigatorHelper.setSkimOver(skimOver)
            }
        var isReselectWhenLayout = true
        private val mPositionDataList = ArrayList<PositionData>()
        private val mObserver = object : DataSetObserver() {
            override fun onChanged() {
                this@CommonNavigator.mNavigatorHelper.totalCount = this@CommonNavigator.adapter!!.count
                this@CommonNavigator.init()
            }

            override fun onInvalidated() {}
        }

        init {
            this.mNavigatorHelper.setNavigatorScrollListener(this)
        }

        override fun notifyDataSetChanged() {
            if (this.adapter != null) {
                this.adapter!!.notifyDataSetChanged()
            }

        }

        private fun init() {
            this.removeAllViews()
            val root: View = if (this.isAdjustMode) {
                LayoutInflater.from(this.context).inflate(net.lucode.hackware.magicindicator.R.layout.pager_navigator_layout_no_scroll, this)
            } else {
                LayoutInflater.from(this.context).inflate(net.lucode.hackware.magicindicator.R.layout.pager_navigator_layout, this)
            }

            this.mScrollView = root.findViewById<View>(net.lucode.hackware.magicindicator.R.id.scroll_view) as HorizontalScrollView
            this.titleContainer = root.findViewById<View>(net.lucode.hackware.magicindicator.R.id.title_container) as LinearLayout
            this.titleContainer!!.setPadding(this.leftPadding, 0, this.rightPadding, 0)
            this.mIndicatorContainer = root.findViewById<View>(net.lucode.hackware.magicindicator.R.id.indicator_container) as LinearLayout
            if (this.isIndicatorOnTop) {
                this.mIndicatorContainer!!.parent.bringChildToFront(this.mIndicatorContainer)
            }

            this.initTitlesAndIndicator()
        }

        open fun onAfterInitTitleView() {

        }

        private fun initTitlesAndIndicator() {
            var i = 0

            val j = this.mNavigatorHelper.totalCount
            while (i < j) {
                val v = this.adapter!!.getTitleView(this.context, i)
                if (v is View) {
                    val view = v as View
                    val lp: ViewGroup.LayoutParams
                    if (this.isAdjustMode) {
                        lp = FrameLayout.LayoutParams(0, -1)
//                        lp.weight = this.adapter!!.getTitleWeight(this.context, i)
                    } else {
                        lp = FrameLayout.LayoutParams(-2, -1)
                    }

                    this.titleContainer!!.addView(view, lp)
                }
                ++i
            }

            if (this.adapter != null) {
                this.pagerIndicator = this.adapter!!.getIndicator(this.context)
                if (this.pagerIndicator is View) {
                    val lp = android.widget.FrameLayout.LayoutParams(-1, -1)
                    this.mIndicatorContainer!!.addView(this.pagerIndicator as View?, lp)
                    onAfterInitTitleView()
                }
            }

        }

        override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
            super.onLayout(changed, left, top, right, bottom)
            if (this.adapter != null) {
                this.preparePositionData()
                if (this.pagerIndicator != null) {
                    this.pagerIndicator!!.onPositionDataProvide(this.mPositionDataList)
                }

                if (this.isReselectWhenLayout && this.mNavigatorHelper.scrollState == 0) {
                    this.onPageSelected(this.mNavigatorHelper.currentIndex)
                    this.onPageScrolled(this.mNavigatorHelper.currentIndex, 0.0f, 0)
                }
            }

        }

        private fun preparePositionData() {
            this.mPositionDataList.clear()
            var i = 0

            val j = this.mNavigatorHelper.totalCount
            while (i < j) {
                val data = PositionData()
                val v = this.titleContainer!!.getChildAt(i)
                if (v != null) {
                    data.mLeft = v.left
                    data.mTop = v.top
                    data.mRight = v.right
                    data.mBottom = v.bottom
                    if (v is IMeasurablePagerTitleView) {
                        val view = v as IMeasurablePagerTitleView
                        data.mContentLeft = view.contentLeft
                        data.mContentTop = view.contentTop
                        data.mContentRight = view.contentRight
                        data.mContentBottom = view.contentBottom
                    } else {
                        data.mContentLeft = data.mLeft
                        data.mContentTop = data.mTop
                        data.mContentRight = data.mRight
                        data.mContentBottom = data.mBottom
                    }
                }

                this.mPositionDataList.add(data)
                ++i
            }

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            if (this.adapter != null) {
                this.mNavigatorHelper.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (this.pagerIndicator != null) {
                    this.pagerIndicator!!.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                if (this.mScrollView != null && this.mPositionDataList.size > 0 && position >= 0 && position < this.mPositionDataList.size) {
                    if (this.isFollowTouch) {
                        val currentPosition = Math.min(this.mPositionDataList.size - 1, position)
                        val nextPosition = Math.min(this.mPositionDataList.size - 1, position + 1)
                        val current = this.mPositionDataList.get(currentPosition) as PositionData
                        val next = this.mPositionDataList.get(nextPosition) as PositionData
                        val scrollTo = current.horizontalCenter().toFloat() - this.mScrollView!!.width.toFloat() * this.scrollPivotX
                        val nextScrollTo = next.horizontalCenter().toFloat() - this.mScrollView!!.width.toFloat() * this.scrollPivotX
                        this.mScrollView!!.scrollTo((scrollTo + (nextScrollTo - scrollTo) * positionOffset).toInt(), 0)
                    } else if (!this.isEnablePivotScroll) {
                    }
                }
            }

        }

        override fun onPageSelected(position: Int) {
            if (this.adapter != null) {
                this.mNavigatorHelper.onPageSelected(position)
                if (this.pagerIndicator != null) {
                    this.pagerIndicator!!.onPageSelected(position)
                }
            }

        }

        override fun onPageScrollStateChanged(state: Int) {
            if (this.adapter != null) {
                this.mNavigatorHelper.onPageScrollStateChanged(state)
                if (this.pagerIndicator != null) {
                    this.pagerIndicator!!.onPageScrollStateChanged(state)
                }
            }

        }

        override fun onAttachToMagicIndicator() {
            this.init()
        }

        override fun onDetachFromMagicIndicator() {}

        override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
            if (this.titleContainer != null) {
                val v = this.titleContainer!!.getChildAt(index)
                if (v is IPagerTitleView) {
                    (v as IPagerTitleView).onEnter(index, totalCount, enterPercent, leftToRight)
                }

            }
        }

        override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
            if (this.titleContainer != null) {
                val v = this.titleContainer!!.getChildAt(index)
                if (v is IPagerTitleView) {
                    (v as IPagerTitleView).onLeave(index, totalCount, leavePercent, leftToRight)
                }

            }
        }

        override fun onSelected(index: Int, totalCount: Int) {
            if (this.titleContainer != null) {
                val v = this.titleContainer!!.getChildAt(index)
                if (v is IPagerTitleView) {
                    (v as IPagerTitleView).onSelected(index, totalCount)
                }

                if (!this.isAdjustMode && !this.isFollowTouch && this.mScrollView != null && this.mPositionDataList.size > 0) {
                    val currentIndex = Math.min(this.mPositionDataList.size - 1, index)
                    val current = this.mPositionDataList.get(currentIndex) as PositionData
                    if (this.isEnablePivotScroll) {
                        val scrollTo = current.horizontalCenter().toFloat() - this.mScrollView!!.width.toFloat() * this.scrollPivotX
                        if (this.isSmoothScroll) {
                            this.mScrollView!!.smoothScrollTo(scrollTo.toInt(), 0)
                        } else {
                            this.mScrollView!!.scrollTo(scrollTo.toInt(), 0)
                        }
                    } else if (this.mScrollView!!.scrollX > current.mLeft) {
                        if (this.isSmoothScroll) {
                            this.mScrollView!!.smoothScrollTo(current.mLeft, 0)
                        } else {
                            this.mScrollView!!.scrollTo(current.mLeft, 0)
                        }
                    } else if (this.mScrollView!!.scrollX + this.width < current.mRight) {
                        if (this.isSmoothScroll) {
                            this.mScrollView!!.smoothScrollTo(current.mRight - this.width, 0)
                        } else {
                            this.mScrollView!!.scrollTo(current.mRight - this.width, 0)
                        }
                    }
                }

            }
        }

        override fun onDeselected(index: Int, totalCount: Int) {
            if (this.titleContainer != null) {
                val v = this.titleContainer!!.getChildAt(index)
                if (v is IPagerTitleView) {
                    (v as IPagerTitleView).onDeselected(index, totalCount)
                }

            }
        }

        fun getPagerTitleView(index: Int): IPagerTitleView? {
            return if (this.titleContainer == null) null else this.titleContainer!!.getChildAt(index) as IPagerTitleView
        }
    }
}