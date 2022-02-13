package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spire.doc.*;
import com.spire.doc.documents.BuiltinStyle;
import com.spire.doc.documents.HorizontalAlignment;
import com.spire.doc.documents.ListPatternType;
import com.spire.doc.documents.ListStyle;
import com.spire.doc.documents.ListType;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.ParagraphStyle;
import com.spire.doc.documents.XHTMLValidationType;
import com.spire.doc.fields.TextRange;

import dao.AssetDAO;
import dao.ImpactLevelDAO;
import dao.LikelihoodLevelDAO;
import dao.RiskDAO;
import dao.RiskLevelDAO;
import dao.SystemDAO;
import dao.impl.AssetDAOImpl;
import dao.impl.ImpactLevelDAOImpl;
import dao.impl.LikelihoodLevelDAOImpl;
import dao.impl.RiskDAOImpl;
import dao.impl.RiskLevelDAOImpl;
import dao.impl.SystemDAOImpl;
import model.Asset;
import model.ITSystem;
import model.ImpactLevel;
import model.LikelihoodLevel;
import model.Risk;
import model.RiskAssessment;
import model.RiskLevel;
import model.User;

public class Report {
	public static Document test() {
		Document document = new Document();
		document.loadFromFile("D:/Project/Risk-Assessment-Page/asset-manage.html", FileFormat.Html, XHTMLValidationType.None);
		return document;
	}
	public static Document createDocument(HttpServletRequest request) {
		// tạo Word document
        Document document = new Document();
        Section section = document.addSection();
        Paragraph para;
        TextRange tr;
        
        ListStyle numberList = new ListStyle(document, ListType.Numbered);
        numberList.setName("numberList");

        ListStyle bulletList= new ListStyle(document, ListType.Bulleted);
        bulletList.setName("bulletList");

        document.getListStyles().add(numberList);
        document.getListStyles().add(bulletList);
  
        

        ParagraphStyle style = new ParagraphStyle(document);
        style.setName("paraStyle");
        style.getCharacterFormat().setFontName("Arial");
        style.getCharacterFormat().setFontSize(11f);
        document.getStyles().add(style);
  
        // Tiêu đề
        Paragraph heading = section.addParagraph();
        tr = heading.appendText("BÁO CÁO ĐÁNH GIÁ RỦI RO");
        heading.applyStyle(BuiltinStyle.Title);
        tr.getCharacterFormat().setFontName("Times New Roman");
        
        // Thông tin người xuất báo cáo
        User user = MyUtils.getUserInSession(request);
        String name = "";
        if(user.getName() == null || user.getName() == "") {
        	name = user.getUsername();
        } else {
        	name = user.getName();
        }
        para = section.addParagraph();
        para.appendText("Người thực hiện: " + name).getCharacterFormat().setItalic(true);;

        		
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy ");  
        String strDate = dateFormat.format(date);  
        para = section.addParagraph();
        para.appendText("Thời điểm thực hiện: " + strDate).getCharacterFormat().setItalic(true);
        
  
        // Phần I: Introduction
        SystemDAO systemDAO = new SystemDAOImpl();
		ITSystem system = systemDAO.getByUserId(user.getId());
		
        Paragraph subheading = section.addParagraph();
        tr = subheading.appendText("I. Tổng quan hệ thống");
        subheading.applyStyle(BuiltinStyle.Heading_3);
        tr.getCharacterFormat().setFontName("Times New Roman");
  
        para = section.addParagraph();
        para.appendText("Tên hệ thống: " + system.getName());
        para.getListFormat().applyStyle(bulletList.getName());
  
       
        para = section.addParagraph();
        para.appendText("Mô tả hệ thống: " + system.getDescription());
        para.getListFormat().applyStyle(bulletList.getName());
        
     // Phần II: Asset
        AssetDAO assetDAO = new AssetDAOImpl();
		List<Asset> assets = assetDAO.getAllAssetsInSystem(system.getId());
		
        subheading = section.addParagraph();
        tr = subheading.appendText("II. Tài sản hệ thống");
        subheading.applyStyle(BuiltinStyle.Heading_3);
        tr.getCharacterFormat().setFontName("Times New Roman");
  
        para = section.addParagraph();
        para.appendText("Hệ thống bao gồm: " + assets.size() + " tài sản. Danh sách tài sản được liệt kê dưới đây.");
        
        for(Asset asset: assets) {
        	 para = section.addParagraph();
             para.appendText(asset.getName());
             para.getListFormat().applyStyle(numberList.getName());
             
             para = section.addParagraph();
             para.appendText(asset.getDescription());  
             para.getFormat().setFirstLineIndent(40);
        }
       
       // Phần 3: Thang điểm đánh giá rủi ro
        LikelihoodLevelDAO likelihoodLevelDAO = new LikelihoodLevelDAOImpl();
		Map<Integer, LikelihoodLevel> likelihoods = likelihoodLevelDAO.getAllInSystem(system.getId());
		
		ImpactLevelDAO impactLevelDAO = new ImpactLevelDAOImpl();
		Map<Integer, ImpactLevel> impacts = impactLevelDAO.getAllInSystem(system.getId());
		
		RiskLevelDAO riskLevelDAO = new RiskLevelDAOImpl();
		Map<Integer, RiskLevel> risk_levels = riskLevelDAO.getAllInSystem(system.getId());
		
		List<LikelihoodLevel> likelihood_list = MyUtils.mapToList(likelihoods);
		List<ImpactLevel> impact_list = MyUtils.mapToList(impacts);
		List<RiskLevel> risk_level_list = MyUtils.mapToList(risk_levels);
		
        subheading = section.addParagraph();
        tr = subheading.appendText("III. Thang điểm đánh giá rủi ro");
        subheading.applyStyle(BuiltinStyle.Heading_3);
        tr.getCharacterFormat().setFontName("Times New Roman");
        
        para = section.addParagraph();
        para.appendText("Điểm số đánh giá từ 0 đến 100 điểm. Dưới đây là chi tiết các thang điểm.");
        
        ListStyle numberList3 = new ListStyle(document, ListType.Numbered);
        numberList3.setName("numberList3");
        document.getListStyles().add(numberList3);

  
        para = section.addParagraph();
        para.appendText("Thang điểm mức độ tác động (Impact)");
        para.getListFormat().applyStyle(numberList3.getName());
        for(ImpactLevel impact: impact_list) {
       	 	para = section.addParagraph();
            para.appendText(impact.getLevel() + ": " + impact.getScore());
            para.getListFormat().applyStyle(bulletList.getName());
            para.getListFormat().setListLevelNumber(1);
        }
        
        para = section.addParagraph();
        para.appendText("Thang điểm khả năng xảy ra (Threat Likelihood)");
        para.getListFormat().applyStyle(numberList3.getName());
        for(LikelihoodLevel likelihood: likelihood_list) {
       	 	para = section.addParagraph();
            para.appendText(likelihood.getLevel() + ": " + likelihood.getScore());
            para.getListFormat().applyStyle(bulletList.getName());
            para.getListFormat().setListLevelNumber(1);
        }
        
        para = section.addParagraph();
        para.appendText("Thang đo rủi ro (Risk Scale)");
        para.getListFormat().applyStyle(numberList3.getName());
        for(RiskLevel riskLevel: risk_level_list) {
       	 	para = section.addParagraph();
       	 	if(riskLevel.getRange_min() == riskLevel.getRange_max()) {
       	 		para.appendText(riskLevel.getLevel() + ": " + riskLevel.getRange_min());
       	 	} else {
       	 		para.appendText(riskLevel.getLevel() + ": từ " + riskLevel.getRange_min() + " đến " + riskLevel.getRange_max());
       	 	}
            
            para.getListFormat().applyStyle(bulletList.getName());
            para.getListFormat().setListLevelNumber(1);
        }
        
        para = section.addParagraph();
        para.appendText("Ma trận mức độ rủi ro (Risk-level matrix)");
        para.getListFormat().applyStyle(numberList3.getName());
        
        Table table = section.addTable(true);
        int rowCount = likelihood_list.size() + 2;
        int columnCount = impact_list.size() + 1;
        table.resetCells(rowCount,columnCount);
        table.applyHorizontalMerge(0, 1, columnCount - 1);
        para = table.getRows().get(0).getCells().get(1).addParagraph();
        para.appendText("Mức độ tác động").getCharacterFormat().setBold(true);
        para.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
        
        table.applyVerticalMerge(0, 0, 1);
        para = table.getRows().get(0).getCells().get(0).addParagraph();
        para.appendText("Khả năng xảy ra").getCharacterFormat().setBold(true);
        para.getFormat().setHorizontalAlignment(HorizontalAlignment.Justify);
        
        for(int j = 1; j < columnCount; j++) {
        	ImpactLevel i = impact_list.get(j - 1);
        	String data_i = i.getLevel() + " (" + i.getScore() + ")";
            table.getRows().get(1).getCells().get(j).addParagraph().appendText(data_i);
        }
        
        for(int i = 2; i < rowCount; i++) {
        	LikelihoodLevel likelihood = likelihood_list.get(i - 2);
        	String data_l = likelihood.getLevel() + " (" + likelihood.getScore() + ")";
            table.getRows().get(i).getCells().get(0).addParagraph().appendText(data_l);
            
            for(int j = 1; j < columnCount; j++) {
            	ImpactLevel impact = impact_list.get(j - 1);
            	double score = (double)likelihood.getScore()*impact.getScore()/100;
            	String label = countRiskLevel(risk_level_list, score);
            	table.getRows().get(i).getCells().get(j).addParagraph().appendText(label);
            }
        	
        }
        
        // Phần IV: Kết quả đánh giá rủi ro
        RiskDAO riskDAO = new RiskDAOImpl();
		List<Risk> risks = riskDAO.getAllRisksInSystem(system.getId());
		List<RiskAssessment> list = processRiskScore(risks, likelihoods, impacts, risk_levels);
		
		int num_risk_system = riskDAO.getNumRisksInSystem(system.getId());
		int num_risk_assessment = riskDAO.getNumRisksInAssessment(system.getId());
  
		subheading = section.addParagraph();
	    tr = subheading.appendText("IV. Kết quả đánh giá rủi ro");
	    subheading.applyStyle(BuiltinStyle.Heading_3);
	    tr.getCharacterFormat().setFontName("Times New Roman");
       
	    para = section.addParagraph();
        para.appendText("Hệ thống đã xác định: " +  num_risk_system + " rủi ro. Trong đó: ");
        
        para = section.addParagraph();
	 	para.appendText("Đã đánh giá: " + num_risk_assessment);
        para.getListFormat().applyStyle(bulletList.getName());
        
        para = section.addParagraph();
	 	para.appendText("Chưa đánh giá: " + (num_risk_system - num_risk_assessment));
        para.getListFormat().applyStyle(bulletList.getName());
        
        para = section.addParagraph();
	 	para.appendText("Danh sách chi tiết các rủi ro được liệt kê dưới đây.");
  
        ListStyle numberList4 = new ListStyle(document, ListType.Numbered);
        numberList4.setName("numberList4");
        document.getListStyles().add(numberList4);
        for(RiskAssessment r: list) {
        	Risk risk = r.getRisk();
        	LikelihoodLevel likelihood = r.getLikelihoodLevel();
        	ImpactLevel impact = r.getImpactLevel();
        	RiskLevel riskLevel = r.getRiskLevel();
        	
       	 	para = section.addParagraph();
       	 	para.appendText(risk.getShort_description());
       	 	para.getListFormat().applyStyle(numberList4.getName());
            
       	 	para = section.addParagraph();
    	 	para.appendText("Lỗ hổng: " + risk.getFlaw());
            para.getListFormat().applyStyle(bulletList.getName());
            para.getListFormat().setListLevelNumber(1);
            
            para = section.addParagraph();
    	 	para.appendText("Nguồn đe dọa: " + risk.getThreat());
            para.getListFormat().applyStyle(bulletList.getName());
            para.getListFormat().setListLevelNumber(1);
            
            para = section.addParagraph();
    	 	para.appendText("Khả năng xảy ra: " + likelihood.getLevel());
            para.getListFormat().applyStyle(bulletList.getName());
            para.getListFormat().setListLevelNumber(1);
            
            para = section.addParagraph();
    	 	para.appendText("Mức độ tác động: " + impact.getLevel());
            para.getListFormat().applyStyle(bulletList.getName());
            para.getListFormat().setListLevelNumber(1);
            
            para = section.addParagraph();
    	 	para.appendText("Xếp hạng rủi ro: " + riskLevel.getLevel());
            para.getListFormat().applyStyle(bulletList.getName());
            para.getListFormat().setListLevelNumber(1);
            
            para = section.addParagraph();
            if(risk.getSolution() == null || risk.getSolution() == "") {
            	para.appendText("Biện pháp kiểm soát: Chưa có");
            } else {
            	para.appendText("Biện pháp kiểm soát: " + risk.getSolution());
            }
            para.getListFormat().applyStyle(bulletList.getName());
            para.getListFormat().setListLevelNumber(1);
        }
        
        
        
        // Phần V: Tổng kết
		subheading = section.addParagraph();
	    tr = subheading.appendText("V. Tổng kết");
	    subheading.applyStyle(BuiltinStyle.Heading_3);
	    tr.getCharacterFormat().setFontName("Times New Roman");
	    
	    para = section.addParagraph();
        para.appendText("Hệ thống đã xác định: " + list.size() + " rủi ro. Kết quả đánh giá được tổng kết trong bảng dưới đây:");
        
        String th[] = new String[]{"STT", "Mô tả ngắn", "Mức độ rủi ro", "Biện pháp kiểm soát"};
        
        table = section.addTable(true);
        rowCount = list.size() + 1;
        columnCount = 4;
        table.resetCells(rowCount,columnCount);
        
        for(int j = 0; j < columnCount; j++) {
            table.getRows().get(0).getCells().get(j).addParagraph().appendText(th[j]);
        }
        
        for(int i = 0; i < list.size(); i++) {
            table.getRows().get(i + 1).getCells().get(0).addParagraph().appendText((i + 1) + "");
            table.getRows().get(i + 1).getCells().get(1).addParagraph().appendText(list.get(i).getRisk().getShort_description());
            table.getRows().get(i + 1).getCells().get(2).addParagraph().appendText(list.get(i).getRiskLevel().getLevel());
            table.getRows().get(i + 1).getCells().get(3).addParagraph().appendText(list.get(i).getRisk().getSolution());
        }
	    //
        for (int i = 0; i < section.getParagraphs().getCount(); i++) {
            section.getParagraphs().get(i).getFormat().setAfterAutoSpacing(true);
        }
  
        return document;
	}
	
	private static List<RiskAssessment> processRiskScore(List<Risk> risks, Map<Integer, LikelihoodLevel> likelihoods, Map<Integer, ImpactLevel> impacts, Map<Integer, RiskLevel> risk_levels) {
		List<RiskAssessment> list = new ArrayList<RiskAssessment>();
		
		for(Risk risk: risks) {
			LikelihoodLevel likelihood = likelihoods.get(risk.getLikelihood_level_id());
			ImpactLevel impact = impacts.get(risk.getImpact_level_id());
			
			double risk_score = -1;
			if(likelihood != null && impact != null) {
				risk_score = (double)likelihood.getScore()*impact.getScore()/100;
			}
			
			if(risk_score == -1) {
				RiskLevel risk_level = null;
				RiskAssessment tmp = new RiskAssessment(risk, likelihood, impact, risk_level);
				list.add(tmp);
				continue;
			}
			
			for(int key: risk_levels.keySet()) {
				RiskLevel risk_level = risk_levels.get(key);
				if(risk_score >= risk_level.getRange_min() && risk_score <= risk_level.getRange_max()) {
					RiskAssessment tmp = new RiskAssessment(risk, likelihood, impact, risk_level);
					list.add(tmp);
					break;
				}
			}
			
		}
		
		return list;
	}
	
	
	private static String countRiskLevel(List<RiskLevel> list, double score) {
		for(RiskLevel r: list) {
			if(score >= r.getRange_min() && score <= r.getRange_max()) {
				return r.getLevel();
			}
		}
		return "";
	}
}
