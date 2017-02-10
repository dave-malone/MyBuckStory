package com.mybuckstory.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ContestPrize extends AbstractAuditable{
	
	private Integer rank;
	private Contest contest;
	private Prize prize;
	private Badge badge;
	
	private Story winningStory;
	private Image winningImage;	
	
	private Set<MemberFeedItem> memberFeedItems = Collections.synchronizedSet(new HashSet<MemberFeedItem>());

	public int compareTo(Object o) {
		if(o == this){
			return 0;
		}
		
		if(!(o instanceof ContestPrize)){
			return -1;
		}
		
		ContestPrize prize = (ContestPrize)o;
		
		if(prize.rank != null && this.rank != null){
			return this.rank.compareTo(prize.rank);
		}else if(prize.rank == null && this.rank != null){
			return 1;
		}else if(prize.rank != null && this.rank == null){
			return -1;
		}
		
		return 0;
	}
	
	
	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	public Prize getPrize() {
		return prize;
	}

	public void setPrize(Prize prize) {
		this.prize = prize;
	}

	public Badge getBadge() {
		return badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
	}

	public Story getWinningStory() {
		return winningStory;
	}

	public void setWinningStory(Story winningStory) {
		this.winningStory = winningStory;
	}

	public Image getWinningImage() {
		return winningImage;
	}

	public void setWinningImage(Image winningImage) {
		this.winningImage = winningImage;
	}

	public Set<MemberFeedItem> getMemberFeedItems() {
		return memberFeedItems;
	}

	public void setMemberFeedItems(Set<MemberFeedItem> memberFeedItems) {
		this.memberFeedItems = memberFeedItems;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
}
