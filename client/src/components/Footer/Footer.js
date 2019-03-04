import React, { Component } from "react";

class Footer extends Component {
  render() {
    return (
      <div className="footer">
        <div className="footer-info row">
          <div className="col-md-6 col-sm-6 col-xs-12 footer-contact">
            <h2>Contact</h2>
            <ul>
              <li>
                <i className="fa fa-angle-right" />
                <a href="#">About Lazada</a>
              </li>
              <li>
                <i className="fa fa-angle-right" />
                <a href="#">Sell on Lazada</a>
              </li>
              <li>
                <i className="fa fa-angle-right" />
                <a href="#">Afﬁliate Program</a>
              </li>
              <li>
                <i className="fa fa-angle-right" />
                <a href="#">Terms &amp; Conditions</a>
              </li>
              <li>
                <i className="fa fa-angle-right" />
                <a href="#">Privacy Policy</a>
              </li>
              <li>
                <i className="fa fa-angle-right" />
                <a href="#">Press &amp; Media</a>
              </li>
            </ul>
          </div>
          <div className="col-md-6 col-sm-6 col-xs-12 footer-subcribe">
            <h2>Subcribe</h2>
            <form>
              <div className="form-container row">
                <div className="form-input-email">
                  <input type="email" placeholder="Your email...." />
                </div>
                <div className="form-input-gender">
                  <div className="male-gender">
                    <input type="radio" />
                    <p>Male</p>
                  </div>
                  <div className="female-gender">
                    <input type="radio" />
                    <p>Female</p>
                  </div>
                </div>
                <div className="form-button">
                  <button type="submit">Subcribe</button>
                </div>
              </div>
            </form>
            <div className="footer-download">
              <div className="footer-logo">
                <img src={process.env.PUBLIC_URL + '/assets/images/icon.png'} className="img-icon" alt="" />
                <div className="download-text">
                  <span>EFFORTLESS SHOPPING</span> <span>Download App</span>
                </div>
              </div>
              <div className="icon-donwload-apple">
                <img src={process.env.PUBLIC_URL + '/assets/images/app-download-img.png'} alt="" />
              </div>
              <div className="icon-donwload-android">
                <img src={process.env.PUBLIC_URL + './assets/images/android-download-app.png'} alt="" />
              </div>
            </div>
          </div>
        </div>
        <div className="footer-branch">
          
          <div className="footer-payment">
            <h2>Payment method</h2>

            <div className="row">
                <div className="payment-icon col-md-2 col-sm-2 col-xs-12">
                <img src={process.env.PUBLIC_URL + '/assets/images/icon-payment/visa.jpg'} alt="" />
                </div>
                <div className="payment-icon col-md-2 col-sm-2 col-xs-12">
                <img src={process.env.PUBLIC_URL + '/assets/images/icon-payment/master-card.png'}alt="" />
                </div>
                <div className="payment-icon col-md-2 col-sm-2 col-xs-12">
                <img src={process.env.PUBLIC_URL + '/assets/images/icon-payment/jcb.png'}alt="" />
                </div>
                <div className="payment-icon col-md-2 col-sm-2 col-xs-12">
                <img
                    src={process.env.PUBLIC_URL + '/assets/images/icon-payment/cash-on-delivery.png'}
                    alt=""
                />
                </div>
                <div className="payment-icon col-md-2 col-sm-2 col-xs-12">
                <img src={process.env.PUBLIC_URL + '/assets/images/icon-payment/napas.png'} alt="" />
                </div>
                <div className="payment-icon col-md-2 col-sm-2 col-xs-12">
              <img src={process.env.PUBLIC_URL + '/assets/images/icon-payment/pig.jpg'} alt="" />
            </div>
            </div>
          </div>
          
          <div className="footer-delivery">
            <h2>Delivery Method</h2>
            <div className="row">
                <div className="delivery-icon col-md-6 col-sm-6 col-xs-6">
                <img src={process.env.PUBLIC_URL + '/assets/images/icon-delivery-branch/1.png'} alt="" />
                </div>
                <div className="delivery-icon col-md-6 col-sm-6 col-xs-6">
                <img src={process.env.PUBLIC_URL + '/assets/images/icon-delivery-branch/2.png'} alt="" />
                </div>
                <div className="delivery-icon col-md-6 col-sm-6 col-xs-6">
                <img src={process.env.PUBLIC_URL + '/assets/images/icon-delivery-branch/3.png'} alt="" />
                </div>
                <div className="delivery-icon col-md-6 col-sm-6 col-xs-6">
                <img src={process.env.PUBLIC_URL + '/assets/images/icon-delivery-branch/4.png'} alt="" />
                </div>
                <div className="delivery-icon col-md-6 col-sm-6 col-xs-6">
                <img src={process.env.PUBLIC_URL + '/assets/images/icon-delivery-branch/5.png'} alt="" />
                </div>
                <div className="delivery-icon col-md-6 col-sm-6 col-xs-6">
                <img src={process.env.PUBLIC_URL + '/assets/images/icon-delivery-branch/6.jpg'} alt="" />
                </div>
                <div className="delivery-icon col-md-6 col-sm-6 col-xs-6">
                <img src={process.env.PUBLIC_URL + '/assets/images/icon-delivery-branch/7.png'} alt="" />
                </div>
                <div className="delivery-icon col-md-6 col-sm-6 col-xs-6">
              <img src={process.env.PUBLIC_URL + '/assets/images/icon-delivery-branch/8.png'} alt="" />
            </div>
            </div>
          </div>
          
          <div className="footer-cetificate">
            <h2>Certification</h2>
            <div className="row">
                <div className="certificate-icon">
                    <div className="certificate-contain col-md-12 col-sm-12 col-xs-12">
                        <img src={process.env.PUBLIC_URL + '/assets/images/icon-verify/2.png'} />
                        <img src={process.env.PUBLIC_URL + '/assets/images/icon-verify/3.png'} />
                    </div>
                </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Footer;
