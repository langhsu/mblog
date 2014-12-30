/**
 * 
 */
package test;

import junit.framework.TestCase;
import mtons.modules.utils.PreviewHtmlUtils;

import org.junit.Test;

/**
 * @author langhsu
 *
 */
public class HtmlCutTest extends TestCase {
	
	@Test
	public void testCut() {
		String text = "简单的教育题材却让我数次揪心流泪。感叹即使真心对待，认真付出也难以让结果与之相符。男主角的眼神里充满忧伤，童年不堪的经历穿插全片，我的确会像他一样，对于不堪回首的过去，你连想都不敢往下想，搭起围墙把它封起来，不碰以为会遗忘。影片里的对话台词不多，多数是男主角叙述与旁白，但却处处击中泪点。我们需要保持阅读，独立思考，才能保护被24小时压迫的心灵。才能保护最初的信仰。善良不是天生的，是在经历过不堪，痛苦，坚强过后的感悟。因为了解过孤独害怕和无助，所以当看到身边的不幸时才会更懂得感同身受给予关怀。我们都在挣脱这个太过于复杂的世界。被困，挣扎，超脱。无限循环着。";
	    System.out.println(text.length());
		String cuted = PreviewHtmlUtils.truncateHTML(text, 200);
		System.out.println(cuted.length());
	}

}
